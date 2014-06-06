package test.akka.cql.client

import akka.actor.{Props, ActorSystem}
import java.net.InetSocketAddress
import arimitsu.sf.akka.cqlclient.{CqlActor, Configuration, CqlClient}
import arimitsu.sf.akka.cqlclient.events.{Event, EventCallback}
import scala.concurrent.ExecutionContext

/**
 * Created by sxend on 14/05/31.
 */
object Main {
  def main(args: Array[String]): Unit = {

    implicit val serverSystem = ActorSystem("serverSystem")
    val configuration = Configuration(Seq(new InetSocketAddress("127.0.0.1", 9042)), 10)
    val client = CqlClient(configuration)
    Thread.sleep(1000L)
    val f = client.options()

    import serverSystem.dispatcher
    f.onComplete{
      _ => println("end")
    }
  }
}

