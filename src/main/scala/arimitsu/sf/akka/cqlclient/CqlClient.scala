package arimitsu.sf.akka.cqlclient

import akka.actor.{Props, ActorSystem, ActorRef}
import scala.concurrent.{Promise, Future}
import arimitsu.sf.cql.v3.messages.results.Prepared
import arimitsu.sf.akka.cqlclient.message._
import arimitsu.sf.cql.v3.messages._
import arimitsu.sf.akka.cqlclient.message.AuthResponse
import arimitsu.sf.akka.cqlclient.message.Execute
import arimitsu.sf.akka.cqlclient.message.Register
import arimitsu.sf.akka.cqlclient.message.Options
import arimitsu.sf.akka.cqlclient.message.Startup
import arimitsu.sf.akka.cqlclient.message.Prepare
import arimitsu.sf.akka.cqlclient.message.Batch
import arimitsu.sf.akka.cqlclient.message.Query

/**
 * Created by sxend on 14/05/31.
 */
object Cluster {
  def apply(clusterConfig: Configuration)(implicit actorSystem: ActorSystem): Cluster = {
    val callback = new EventHandler(clusterConfig)
    val cluster = Iterator.continually(clusterConfig.clusterAddress.flatMap {
      node => (0 until clusterConfig.connectionPerHost).map {
        i => {
          val nodeConfig = NodeConfiguration(node, clusterConfig.flags, clusterConfig.compression)
          val cqlActor = actorSystem.actorOf(Props(classOf[CqlActor], nodeConfig, callback))
          new CqlClient(nodeConfig, cqlActor, callback)
        }
      }
    }).flatten
    new Cluster(cluster)
  }
}

class Cluster(cluster: Iterator[CqlClient])(implicit actorSystem: ActorSystem) {

  def runWith[A](action: CqlClient => A): A = {
    action(cluster.next())
  }

  def client: CqlClient = cluster.next()

  //  def startup[A](action: Either[Authenticate, Ready] => A)(implicit executionContext: ExecutionContext) = {
  //    val client = cluster.next()
  //    val result = cluster.map {
  //      client =>
  //        (client.startup(), client)
  //
  //    }
  //    new {
  //      def runWith[B](callback: CqlClient => B) = {
  //        result.map {
  //          r => r._1.map {
  //            case a => action(a)
  //          }.onSuccess {
  //            case a =>
  //              if (r._2 == client) {
  //                callback(client)
  //              }
  //          }
  //        }
  //      }
  //    }
  //
  //  }
}

class CqlClient(nodeConfig: NodeConfiguration, cqlActor: ActorRef, eventHandler: EventHandler) {
  def options(): Future[Supported] = {
    val options = Options(Promise[Supported]())
    cqlActor ! options
    options.promise.future
  }

  def startup(): Future[Either[Authenticate, Ready]] = {
    val startup = Startup(Promise[Either[Authenticate, Ready]]())
    cqlActor ! startup
    startup.promise.future
  }

  def query(string: String, parameter: QueryParameters): Future[Result] = {
    val query = Query(string, parameter, Promise[Result]())
    cqlActor ! query
    query.promise.future
  }

  def prepare(queryString: String): Future[Prepared] = {
    val prepare = Prepare(queryString, Promise[Prepared]())
    cqlActor ! prepare
    prepare.promise.future
  }

  def execute(id: Array[Byte], parameter: QueryParameters): Future[Result] = {
    val execute = Execute(id, parameter, Promise[Result]())
    cqlActor ! execute
    execute.promise.future
  }


  def batch(): Future[Result] = {
    val batch = Batch(Promise[Result]())
    cqlActor ! batch
    batch.promise.future
  }

  def register(events: List[String]): Future[Ready] = {
    val register = Register(events, Promise[Ready]())
    cqlActor ! register
    register.promise.future
  }

  def authResponse(token: Array[Byte]): Future[AuthSuccess] = {
    val authResponse = AuthResponse(token, Promise[AuthSuccess]())
    cqlActor ! authResponse
    authResponse.promise.future
  }

}
