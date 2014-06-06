package arimitsu.sf.akka.cqlclient.events

import akka.actor.ActorSystem
import arimitsu.sf.akka.cqlclient.Configuration

/**
 * Created by sxend on 2014/06/06.
 */
class EventCallback(configuration: Configuration)(implicit actorSystem: ActorSystem) {
  type Handle = PartialFunction[Event, Unit]

  def handle: Handle = {
    case _ =>
  }
}
