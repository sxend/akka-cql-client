package arimitsu.sf.cql.v3.message

;


import arimitsu.sf.cql.v3.{Header, Frame, Opcode, Version}
;

/**
 * Created by sxend on 14/06/07.
 */
case class Options(streamId: Short, flags: Byte) extends Request {
  def toFrame: Frame = {
    Frame(Header(Version.REQUEST, flags, streamId, Opcode.OPTIONS), None)
  }
}
