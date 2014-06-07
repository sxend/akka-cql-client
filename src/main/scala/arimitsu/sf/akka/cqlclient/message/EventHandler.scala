package arimitsu.sf.akka.cqlclient.message

import akka.actor.ActorSystem
import arimitsu.sf.akka.cqlclient.Configuration
import arimitsu.sf.cql.v3.messages.{SCHEMA_CHANGE, STATUS_CHANGE, TOPOLOGY_CHANGE, CassandraEvent}

/**
 * Created by sxend on 2014/06/06.
 */
class EventHandler(configuration: Configuration)(implicit actorSystem: ActorSystem) {
  type Handle = PartialFunction[CassandraEvent, Unit]

  def handle: Handle = {
    case TOPOLOGY_CHANGE =>
    case STATUS_CHANGE =>
    case SCHEMA_CHANGE =>
  }
}
