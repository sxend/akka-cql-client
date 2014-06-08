package arimitsu.sf.akka.cqlclient

import akka.actor.Actor
import java.util.concurrent.atomic.AtomicReference
import arimitsu.sf.akka.cqlclient.message.{Startup, Message, Options, EventHandler}
import akka.io.{Tcp, IO}
import akka.io.Tcp._
import arimitsu.sf.cql.v3._
import arimitsu.sf.cql.v3.messages.Event
import akka.util.ByteString
import akka.io.Tcp.Connected
import akka.io.Tcp.Received
import akka.io.Tcp.Register
import akka.io.Tcp.Connect
import akka.io.Tcp.CommandFailed
import java.nio.ByteBuffer
import arimitsu.sf.cql.v3.util.Notation

/**
 * Created by sxend on 2014/06/06.
 */
class CqlActor(configuration: Configuration, eventHandler: EventHandler) extends Actor {

  import context.system

  IO(Tcp) ! Connect(configuration.clusterAddress(0))
  val operationMap = scala.collection.mutable.HashMap[Int, Message]()

  override def receive = {
    case CommandFailed(c: Connect) =>
      sender() ! "connect failed"
      context stop self
    case c@Connected(remote, local) =>
      val connection = sender()
      connection ! Register(self)
      val streamReference: AtomicReference[Short] = new AtomicReference[Short](1.toShort)
      def send(message: Message, process: (Short) => ByteBuffer) = {
        val streamId: Short = streamReference.get()
        val result = streamReference.compareAndSet(streamId, (streamId + 1).toShort)
        if (result) {
          operationMap.put(streamId, message)
          connection ! Write(ByteString(process(streamId)))
        } else {
          self ! message
        }
      }
      context.become {
        case option@Options(_) =>
          send(option, (streamId) =>
            arimitsu.sf.cql.v3.messages.Options(streamId, Flags.NONE.value).toFrame.toByteBuffer
          )
        case startup@Startup(_, _) =>
          send(startup, (streamId) =>
            arimitsu.sf.cql.v3.messages.Startup(streamId, Flags.NONE.value, startup.compression).toFrame.toByteBuffer
          )
        case Received(data) =>
          val frame = Frame(data.toByteBuffer)
          import Opcode._

          frame.header.opcode match {
            case ERROR =>
              frame.header.streamId match {
                case 0 =>
                case _ =>
                  val op = operationMap.remove(frame.header.streamId)
                  op.get.error(arimitsu.sf.cql.v3.messages.ErrorParser.parse(frame.body.get))
              }
            case READY =>
              val op = operationMap.remove(frame.header.streamId)
              op.get.apply(frame)
            case AUTHENTICATE =>
              val op = operationMap.remove(frame.header.streamId)
              op.get.apply(frame)
            case RESULT =>
              val op = operationMap.remove(frame.header.streamId)
              op.get.apply(frame)
            case SUPPORTED =>
              val op = operationMap.remove(frame.header.streamId)
              op.get.apply(frame)
            case AUTH_CHALLENGE =>
              val op = operationMap.remove(frame.header.streamId)
              op.get.apply(frame)
            case AUTH_SUCCESS =>
              val op = operationMap.remove(frame.header.streamId)
              op.get.apply(frame)
            case EVENT =>
              if (frame.header.streamId >= 0) throw new RuntimeException("protocol error.")
              eventHandler.handle(Event(frame))
            case _ =>
          }
      }
    case message: Message => self ! message
  }

}
