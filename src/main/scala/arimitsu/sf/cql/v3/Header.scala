package arimitsu.sf.cql.v3


/**
 * Created by sxend on 14/06/04.
 */

object Header {
  def apply(version: Version, flags: Byte, streamId: Short, opcode: Opcode): Header = {
    new Header(version, flags, streamId, opcode)
  }
}

class Header(val version: Version, val flags: Byte, val streamId: Short, val opcode: Opcode) {}
