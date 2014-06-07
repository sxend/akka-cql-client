package test.akka.cql.client

import akka.actor.ActorSystem
import java.net.InetSocketAddress
import arimitsu.sf.akka.cqlclient.{Configuration, CqlClient}
import scala.util.Success
import scala.util.Failure

/**
 * Created by sxend on 14/05/31.
 */
object Main {
  def main(args: Array[String]): Unit = {
    implicit val serverSystem = ActorSystem("serverSystem")
    val configuration = Configuration(Seq(new InetSocketAddress("127.0.0.1", 9042)), "lz4", 10)
    val client = CqlClient(configuration)
    val f = client.options()
    import serverSystem.dispatcher
    f.onComplete {
      case Success(s) => println(s.parameters)
      case Failure(t) => t.printStackTrace()
    }
  }
}
