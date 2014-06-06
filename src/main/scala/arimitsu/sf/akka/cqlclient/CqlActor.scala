package arimitsu.sf.akka.cqlclient

import akka.actor.{ActorRef, Actor}
import java.util.concurrent.atomic.AtomicReference
import arimitsu.sf.akka.cqlclient.message.Options
import akka.io.{Tcp, IO}
import akka.io.Tcp._
import arimitsu.sf.akka.cqlclient.events.EventCallback
import arimitsu.sf.cql.v3.{Flags, Version, Opcode, Frame}
import akka.util.ByteString
import arimitsu.sf.cql.v3.Frame.Builder
import arimitsu.sf.akka.cqlclient.Configuration
import akka.io.Tcp.Connected
import akka.io.Tcp.Received
import akka.io.Tcp.Connect
import arimitsu.sf.akka.cqlclient.message.Options

/**
 * Created by sxend on 2014/06/06.
 */
class CqlActor(configuration: Configuration, eventCallback: EventCallback) extends Actor {
  import context.system
  IO(Tcp) ! Connect(configuration.clusterAddress(0))
  val connection: AtomicReference[ActorRef] = new AtomicReference[ActorRef]()

  implicit def byteString2Frame(bs: ByteString): Frame = new Frame(bs.asByteBuffer)

  override def receive = {
    case CommandFailed(c: Connect) =>
      println("failed")
      println(c)
      sender() ! "connect failed"
      context stop self
    case c@Connected(remote, local) =>
      println("c")
      val conn = sender()
      conn ! Register(self)
      connection.set(conn)
      context.become{
        case option@Options(promise) =>
          println("option")
          val frame = new Builder()
            .version(Version.REQUEST)
            .flags(Flags.NONE.value)
            .streamId(1.toShort)
            .opcode(Opcode.OPTIONS).build()
          frame.toByteBuffer.array().foreach(print)
          println()
          conn ! Write(ByteString(frame.toByteBuffer))
        case Received(data) =>
          println("received")
          println(data)
        case a:Any => println("a"+a)
        case _ => println("???")
      }
//      println()
//      val conn = sender()
//      connection.set(conn)
//      val frame = new Builder()
//        .version(Version.REQUEST)
//        .flags(Flags.NONE.value)
//        .streamId(1.toShort)
//        .opcode(Opcode.OPTIONS).build()
//      frame.toByteBuffer.array().foreach(print)
//      println()
//      connection.get() ! Write(ByteString(frame.toByteBuffer))
//      println(connection.get())
//      context.become {
//        case Received(data) =>
//          println("become")
//          val frame: Frame = data
//
//      }

  }
}
