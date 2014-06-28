package arimitsu.sf.akka.cqlclient

import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicReference
import scala.Some
import akka.actor.Actor
import akka.io.{Tcp, IO}
import akka.io.Tcp._
import akka.util.ByteString
import arimitsu.sf.cql.v3._
import arimitsu.sf.akka.cqlclient.message._
import arimitsu.sf.akka.cqlclient.message.Message
import arimitsu.sf.akka.cqlclient.message.AuthResponse
import arimitsu.sf.akka.cqlclient.message.Execute
import arimitsu.sf.akka.cqlclient.message.Options
import arimitsu.sf.akka.cqlclient.message.Startup
import arimitsu.sf.akka.cqlclient.message.Prepare
import arimitsu.sf.akka.cqlclient.message.Batch
import arimitsu.sf.akka.cqlclient.message.Query
import arimitsu.sf.akka.cqlclient.message.Register
import scala.collection.JavaConversions._

/**
 * Created by sxend on 2014/06/06.
 */
class CqlActor(nodeConfig: NodeConfiguration, eventHandler: EventHandler) extends Actor {

  import context.system

  IO(Tcp) ! Connect(nodeConfig.nodeAddress)

  override def receive = {
    case CommandFailed(c: Connect) =>
      sender() ! "connect failed"
      context stop self
    case c@Connected(remote, local) =>
      val connection = sender()
      connection ! akka.io.Tcp.Register(self)
      val streamReference: AtomicReference[Short] = new AtomicReference[Short](1.toShort)
      val compression: Compression = nodeConfig.compression
      val operationMap = scala.collection.mutable.HashMap[Short, Message[_]]()
      def sendMessage(message: Message[_], process: (Short) => ByteBuffer) = {
        val streamId: Short = streamReference.get()
        val result = streamReference.compareAndSet(streamId, (streamId + 1).toShort)
        if (result) {
          operationMap.put(streamId, message)
          val bs: ByteString = ByteString(process(streamId))
          println(bs.toString())
          connection ! Write(bs)
        } else {
          self ! message
        }
      }
      context.become {
        case startup: Startup =>
          sendMessage(startup, (streamId) => {
            val default = Map(arimitsu.sf.cql.v3.messages.Startup.CQL_VERSION -> arimitsu.sf.cql.v3.messages.Startup.CQL_VERSION_NUMBER)
            val option = if (Compression.NONE != nodeConfig.compression)
              default + (messages.Startup.OPTION_COMPRESSION -> nodeConfig.compression.name)
            else default
            new messages.Startup(streamId, nodeConfig.flags, option).toFrame.toByteBuffer(Compression.NONE.compressor)
          })
        case option: Options =>
          sendMessage(option, (streamId) =>
            new messages.Options(streamId, nodeConfig.flags).toFrame.toByteBuffer(compression.compressor)
          )
        case query: Query =>
          sendMessage(query, (streamId) =>
            new messages.Query(streamId, nodeConfig.flags, query.string, query.parameter).toFrame.toByteBuffer(compression.compressor)
          )
        case prepare: Prepare =>
          sendMessage(prepare, (streamId) => {
            val frame = new messages.Prepare(streamId, nodeConfig.flags, prepare.query).toFrame
            frame.toByteBuffer(compression.compressor)
          }
          )
        case execute: Execute =>
          sendMessage(execute, (streamId) =>
            new messages.Execute(streamId, nodeConfig.flags, execute.id, execute.parameters).toFrame.toByteBuffer(compression.compressor)
          )
        case batch: Batch =>
          sendMessage(batch, (streamId) =>
            new messages.Batch(streamId, nodeConfig.flags).toFrame.toByteBuffer(compression.compressor)
          )
        case register: Register =>
          sendMessage(register, (streamId) =>
            new messages.Register(streamId, nodeConfig.flags, register.events).toFrame.toByteBuffer(compression.compressor)
          )
        case authResponse: AuthResponse =>
          sendMessage(authResponse, (streamId) =>
            new messages.AuthResponse(streamId, nodeConfig.flags, authResponse.token).toFrame.toByteBuffer(compression.compressor)
          )
        case Received(data) =>
          val frame = new Frame(data.toByteBuffer, compression.compressor)
          import Opcode._
          frame.header.opcode match {
            case ERROR =>
              val e = arimitsu.sf.cql.v3.messages.Error.PARSER.parse(ByteBuffer.wrap(frame.body))
              val op = operationMap.remove(frame.header.streamId)
              op match {
                case Some(s) => s.error(e)
                case None => throw e.toThrowable
              }
            case EVENT =>
              if (frame.header.streamId >= 0) throw new RuntimeException("protocol error.")
              eventHandler.handle(arimitsu.sf.cql.v3.messages.Event.PARSER.parse(ByteBuffer.wrap(frame.body)))
            case _ =>
              operationMap.remove(frame.header.streamId).get.process(frame)
          }
      }
    case message: Message[_] => self ! message
  }

}
