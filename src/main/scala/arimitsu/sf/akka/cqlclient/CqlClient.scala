package arimitsu.sf.akka.cqlclient

import akka.actor.{Props, ActorSystem}
import akka.io.{IO, Tcp}
import akka.io.Tcp.{Received, Connect}
import java.net.InetSocketAddress

/**
 * Created by sxend on 14/05/31.
 */
object CqlClient{
  def props() = Props
}
class CqlClient(val actorSystem:ActorSystem) {
//  def connect(host:String,port:Int) = {
//    actorSystem
//  IO(Tcp) ! Connect(new InetSocketAddress(host,))
//    Received
//  }
}
