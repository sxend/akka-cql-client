package arimitsu.sf.cql.v3.messages;

import java.net.InetSocketAddress;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.util.Notation;
import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public enum Event{
    TOPOLOGY_CHANGE,
    STATUS_CHANGE,
    SCHEMA_CHANGE
}
//object Event {
//  def apply(frame: Frame): Event = {
//    val buffer = frame.body.get
//    val eventType = Notation.getString(buffer)
//    eventType match {
//      case TopologyChange.NAME => TopologyChange(buffer)
//      case StatusChange.NAME => StatusChange(buffer)
//      case SchemaChange.NAME => SchemaChange(buffer)
//    }
//
//  }
//}
//
//sealed abstract class Event {
//  val name: String
//}
//
//object TopologyChange {
//  val NAME: String = "TOPOLOGY_CHANGE"
//
//  def apply(buffer: ByteBuffer): TopologyChange = {
//    val changeType = Notation.getString(buffer)
//    val nodeAddr = Notation.getINet(buffer)
//    new TopologyChange(nodeAddr, changeType match {
//      case "NEW_NODE" => NewNode
//      case "REMOVED_NODE" => RemoveNode
//    })
//  }
//}
//
//case class TopologyChange(inet: InetSocketAddress, changeType: TopologyChangeType) extends Event {
//  override val name: String = TopologyChange.NAME
//}
//
//sealed trait TopologyChangeType
//
//case object NewNode extends TopologyChangeType
//
//case object RemoveNode extends TopologyChangeType
//
//
//object StatusChange {
//  val NAME: String = "STATUS_CHANGE"
//
//  def apply(buffer: ByteBuffer): StatusChange = {
//    val changeType = Notation.getString(buffer)
//    val nodeAddr = Notation.getINet(buffer)
//    new StatusChange(nodeAddr, changeType match {
//      case Up.name => Up
//      case Down.name => Down
//    })
//  }
//}
//
//case class StatusChange(inet: InetSocketAddress, status: StatusChangeType) extends Event {
//  override val name: String = StatusChange.NAME
//}
//
//sealed trait StatusChangeType {
//  val name: String
//}
//
//case object Up extends StatusChangeType {
//  override val name: String = "UP"
//}
//
//case object Down extends StatusChangeType {
//  override val name: String = "DOWN"
//}
//
//object SchemaChange {
//  val NAME = "SCHEMA_CHANGE"
//
//  def apply(buffer: ByteBuffer): SchemaChange = {
//    val changeType = Notation.getString(buffer) match {
//      case CREATED.name => CREATED
//      case UPDATED.name => UPDATED
//      case DROPPED.name => DROPPED
//    }
//    new SchemaChange(changeType, TargetType(buffer))
//  }
//}
//
//case class SchemaChange(changeType: SchemaChangeType, targetType: TargetType) extends Event {
//  override val name: String = SchemaChange.NAME
//}
//
//sealed abstract class SchemaChangeType(val name: String)
//
//case object CREATED extends SchemaChangeType("CREATED")
//
//case object UPDATED extends SchemaChangeType("UPDATED")
//
//case object DROPPED extends SchemaChangeType("DROPPED")
//
//object TargetType {
//  val KEYSPACE = "KEYSPACE"
//  val TABLE = "TABLE"
//  val TYPE = "TYPE"
//
//  def apply(buffer: ByteBuffer): TargetType = {
//    val target = Notation.getString(buffer)
//    target match {
//      case KEYSPACE =>
//        KeySpace(Notation.getString(buffer))
//      case TABLE =>
//        Table(Notation.getString(buffer), Notation.getString(buffer))
//      case TYPE =>
//        Type(Notation.getString(buffer), Notation.getString(buffer))
//    }
//  }
//}
//
//sealed abstract class TargetType(val targetName: String)
//
//case class KeySpace(name: String) extends TargetType(TargetType.KEYSPACE)
//
//case class Table(keySpace: String, name: String) extends TargetType(TargetType.TABLE)
//
//case class Type(keySpace: String, name: String) extends TargetType(TargetType.TYPE)