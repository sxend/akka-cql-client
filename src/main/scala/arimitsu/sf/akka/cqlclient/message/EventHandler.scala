package arimitsu.sf.akka.cqlclient.message

import akka.actor.ActorSystem
import arimitsu.sf.cql.v3.messages.Event
import arimitsu.sf.akka.cqlclient.Configuration

/**
 * Created by sxend on 2014/06/06.
 */
class EventHandler(config: Configuration)(implicit actorSystem: ActorSystem) {
  type Handle = PartialFunction[Event, Unit]

  def handle: Handle = {
//    case (_,_) => ???
//    case StatusChange(_,_) => ???
//    case SchemaChange(_,_) => ???
    ???
  }
}
