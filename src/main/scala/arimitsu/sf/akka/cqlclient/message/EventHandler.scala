package arimitsu.sf.akka.cqlclient.message

import akka.actor.ActorSystem
import arimitsu.sf.akka.cqlclient.Configuration
import arimitsu.sf.cql.v3.messages.Event

/**
 * Created by sxend on 2014/06/06.
 */
class EventHandler(configuration: Configuration)(implicit actorSystem: ActorSystem) {
  type Handle = PartialFunction[Event, Unit]

  def handle: Handle = {
//    case (_,_) => ???
//    case StatusChange(_,_) => ???
//    case SchemaChange(_,_) => ???
    ???
  }
}
