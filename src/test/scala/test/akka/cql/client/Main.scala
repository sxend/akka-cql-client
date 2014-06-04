package test.akka.cql.client

import akka.actor.{Actor, Props, ActorSystem}
import akka.io.{IO, Tcp}
import akka.io.Tcp._
import java.net.InetSocketAddress
import akka.util.ByteString
import akka.io.Tcp.Connected
import akka.io.Tcp.Received
import akka.io.Tcp.Register
import akka.io.Tcp.Connect
import akka.io.Tcp.CommandFailed
import akka.io.Tcp.Bind

/**
 * Created by sxend on 14/05/31.
 */
object Main {
  def main(args: Array[String]): Unit = {

    {
      implicit val serverSystem = ActorSystem("serverSystem")
      IO(Tcp) ! Bind(serverSystem.actorOf(Props[Server]), new InetSocketAddress("localhost", 2000))
    }


    {
      Client.run()

    }

  }
}

class Server extends Actor {


  override def receive: Receive = {

    case CommandFailed(_: Bind) => context stop self

    case c@Connected(remote, local) =>

      val connection = sender()
      val handler = context.actorOf(Props[Handler])
      connection ! Register(handler)

  }


}

class Handler extends Actor {
  override def receive: Actor.Receive = {
    case Received(data) =>
      println("server -> " + data)
  }
}

object Client {
  def run() = {

    implicit val clientSystem = ActorSystem("clientSystem")
    val manager = IO(Tcp)
    manager ! Connect(new InetSocketAddress("localhost", 2000))
  }
}

class Client extends Actor {

  override def receive: Receive = {
    case c@Connected(remote, local) =>
      println("connected")
      val connection = sender()
      connection ! Register(self)
      context become {
        case Received(data) =>
          println()
          connection ! Write(ByteString("hoge"))
      }
  }
}