package arimitsu.sf.akka.cqlclient

import akka.actor.{Props, ActorSystem, ActorRef}
import scala.concurrent.{Promise, Future}
import arimitsu.sf.akka.cqlclient.message.Options
import arimitsu.sf.akka.cqlclient.message.Startup
import arimitsu.sf.akka.cqlclient.message.EventHandler
import arimitsu.sf.cql.v3.messages.{Authenticate, Ready, Supported}
import arimitsu.sf.cql.v3.Flags

/**
 * Created by sxend on 14/05/31.
 */
object CqlClient {
  def apply(configuration: Configuration)(implicit actorSystem: ActorSystem): CqlClient = {
    val callback = new EventHandler(configuration)
    val cqlActor = actorSystem.actorOf(Props(classOf[CqlActor], configuration, callback))
    new CqlClient(configuration, cqlActor, callback)
  }
}

class CqlClient(configuration: Configuration, cqlActor: ActorRef, eventHandler: EventHandler) {
  def options(): Future[Supported] = {
    val options = Options(configuration.flags, Promise[Supported]())
    cqlActor ! options
    options.promise.future
  }

  def startup(options:Map[String,String]): Future[Either[Authenticate, Ready]] = {
    val startup = Startup(configuration.flags, options, Promise[Either[Authenticate, Ready]]())
    cqlActor ! startup
    startup.promise.future
  }
}
