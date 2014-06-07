package arimitsu.sf.cql.v3.messages

import java.net.InetSocketAddress
import arimitsu.sf.cql.v3.Frame
import arimitsu.sf.cql.v3.util.Notation

/**
 * Created by sxend on 14/06/07.
 */
object CassandraEvent {
  def apply(frame: Frame): CassandraEvent = {
    val eventType = Notation.getString(frame.body.get)
    eventType match {
      case TOPOLOGY_CHANGE.name => ???
      case STATUS_CHANGE.name => ???
      case SCHEMA_CHANGE.name => ???
    }

  }
}

sealed abstract class CassandraEvent(val name: String)

case object TOPOLOGY_CHANGE extends CassandraEvent("TOPOLOGY_CHANGE") {

  case class NEW_NODE(inet: InetSocketAddress)

  case class REMOVED_NODE(inet: InetSocketAddress)

}

case object STATUS_CHANGE extends CassandraEvent("STATUS_CHANGE") {

  case class UP(inet: InetSocketAddress)

  case class DOWN(inet: InetSocketAddress)

}

case object SCHEMA_CHANGE extends CassandraEvent("SCHEMA_CHANGE") {

  sealed abstract class ChangeType(name: String)

  case object CREATED extends ChangeType("CREATED")

  case object UPDATED extends ChangeType("UPDATED")

  case object DROPPED extends ChangeType("DROPPED")

  sealed abstract class TargetType

  case class KEYSPACE() extends TargetType {
  }

  case class TABLE() extends TargetType {

  }

  case class TYPE() extends TargetType {

  }

}


