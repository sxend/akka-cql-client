package arimitsu.sf.cql.v3

;

/**
 * Created by sxend on 14/06/04.
 */
object Opcode {

  sealed abstract class Opcode(val value: Byte) {}

  case object ERROR extends Opcode(0x00)

  //req
  case object STARTUP extends Opcode(0x01)

  case object OPTIONS extends Opcode(0x05)

  case object QUERY extends Opcode(0x07)

  case object AUTH_RESPONSE extends Opcode(0x0F)

  case object PREPARE extends Opcode(0x09)

  case object EXECUTE extends Opcode(0x0A)

  case object BATCH extends Opcode(0x0D)

  case object REGISTER extends Opcode(0x0B)

  //res
  case object READY extends Opcode(0x02)

  case object AUTHENTICATE extends Opcode(0x03)

  case object RESULT extends Opcode(0x08)

  case object SUPPORTED extends Opcode(0x06)

  case object AUTH_CHALLENGE extends Opcode(0x0E)

  case object AUTH_SUCCESS extends Opcode(0x10)

  // push
  case object EVENT extends Opcode(0x0C)

  val values = Seq(ERROR, STARTUP, OPTIONS, QUERY, AUTH_RESPONSE, PREPARE, EXECUTE, BATCH, REGISTER, READY, AUTHENTICATE, RESULT, SUPPORTED, AUTH_CHALLENGE, AUTH_SUCCESS, EVENT)

  def valueOf(value: Byte): Opcode = {
    values.find(_.value == value).get
  }
}
