package arimitsu.sf.cql.v3

;

/**
 * Created by sxend on 14/06/04.
 */

object Version {

  case object REQUEST extends Version(0x3)

  case object RESPONSE extends Version(0x83.toByte)

  val values = Seq(REQUEST, RESPONSE)

  def valueOf(value: Byte) = {
    values.find(ver => ver.value == value).get
  }

  sealed abstract class Version(val value: Byte) {
  }

}
