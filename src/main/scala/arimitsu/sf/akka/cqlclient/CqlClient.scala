package arimitsu.sf.akka.cqlclient

import akka.actor.{Props, ActorSystem, ActorRef}
import scala.concurrent.{Promise, Future}
import arimitsu.sf.akka.cqlclient.message.{Options, Supported}
import arimitsu.sf.akka.cqlclient.events.EventCallback

/**
 * Created by sxend on 14/05/31.
 */
object CqlClient {
  def apply(configuration: Configuration)(implicit actorSystem: ActorSystem): CqlClient = {
    val callback = new EventCallback(configuration)
    val cqlActor = actorSystem.actorOf(Props(classOf[CqlActor], configuration, callback))
    new CqlClient(cqlActor, callback)
  }
}

class CqlClient(cqlActor: ActorRef, eventHandler: EventCallback) {
  def options(): Future[Supported] = {
    val options = Options(Promise[Supported]())
    cqlActor ! options
    options.promise.future
  }
}
