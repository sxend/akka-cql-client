package arimitsu.sf.cql.v3.message

import java.nio.ByteBuffer
import arimitsu.sf.cql.v3.message.ErrorCodes.ErrorCodes
import arimitsu.sf.cql.v3.util.BufferUtil


/**
 * Created by sxend on 14/06/07.
 */
object ErrorParser extends ResponseParser[Error] {
  override def parse(body: ByteBuffer): Error = {
    val ec = body.getInt
    Error(ErrorCodes.valueOf(ec), BufferUtil.toString(body))
  }
}

case class Error(errorCode: ErrorCodes, errorMessage: String)

object ErrorCodes {

  sealed abstract class ErrorCodes(val code: Int)

  case object SERVER_ERROR extends ErrorCodes(0x0000)

  case object PROTOCOL_ERROR extends ErrorCodes(0x000A)

  case object BAD_CREDENTIALS extends ErrorCodes(0x0100)

  case object UNAVAILABLE_EXCEPTION extends ErrorCodes(0x1000)

  case object OVERLOADED extends ErrorCodes(0x1001)

  case object IS_BOOTSTRAPPING extends ErrorCodes(0x1002)

  case object TRUNCATE_ERROR extends ErrorCodes(0x1003)

  case object WRITE_TIMEOUT extends ErrorCodes(0x1100)

  case object READ_TIMEOUT extends ErrorCodes(0x1200)

  case object SYNTAX_ERROR extends ErrorCodes(0x2000)

  case object UNAUTHORIZED extends ErrorCodes(0x2100)

  case object INVALID extends ErrorCodes(0x2200)

  case object CONFIG_ERROR extends ErrorCodes(0x2300)

  case object ALREADY_EXISTS extends ErrorCodes(0x2400)

  case object UNPREPARED extends ErrorCodes(0x2500)

  val values = Seq(SERVER_ERROR,
    PROTOCOL_ERROR,
    BAD_CREDENTIALS,
    UNAVAILABLE_EXCEPTION,
    OVERLOADED,
    IS_BOOTSTRAPPING,
    TRUNCATE_ERROR,
    WRITE_TIMEOUT,
    READ_TIMEOUT,
    SYNTAX_ERROR,
    UNAUTHORIZED,
    INVALID,
    CONFIG_ERROR,
    ALREADY_EXISTS,
    UNPREPARED)

  def valueOf(value: Int): ErrorCodes = {
    values.find(_.code == value).get
  }

}