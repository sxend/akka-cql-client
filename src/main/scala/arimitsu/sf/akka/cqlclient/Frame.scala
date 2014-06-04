package arimitsu.sf.akka.cqlclient

import akka.util.ByteString

/**
 * Created by sxend on 14/05/31.
 */
//case class Frame(header: Header, body: ByteString) {

//}

//case class Header(version: Version, flags: Flags, stream: Stream, opcode: Opcode)

abstract class Version(val value: Byte) {

}

object Versions {
  val Req = new Version(0x03) {} // Request frame for this protocol version
  val Res = new Version(Byte.unbox(0x83.asInstanceOf[Object])) {} // Response frame for this protocol version
}