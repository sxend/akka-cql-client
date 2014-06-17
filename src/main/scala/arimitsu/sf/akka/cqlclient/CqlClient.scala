package arimitsu.sf.akka.cqlclient

import akka.actor.{Props, ActorSystem, ActorRef}
import scala.concurrent.{Promise, Future}
import arimitsu.sf.akka.cqlclient.message.{EventHandler, Startup, Query, Options}
import arimitsu.sf.cql.v3.messages.{QueryParameters, Ready, Authenticate, Supported, Result}
import scala.reflect.ClassTag

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

  def query[A: ClassTag](string: String, parameter: QueryParameters): Future[Result] = {
    val tag = implicitly[ClassTag[A]]
    val query = Query(string, parameter, Promise[Result]())
    cqlActor ! query
    query.promise.future
  }
}
