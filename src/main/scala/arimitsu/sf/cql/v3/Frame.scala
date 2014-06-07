package arimitsu.sf.cql.v3

;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/04.
 */
object Frame {
  def apply(byteBuffer: ByteBuffer): Frame = {
    val version = byteBuffer.get()
    val flags = byteBuffer.get()
    val streamId = byteBuffer.getShort
    val opcode = byteBuffer.get()
    val header = new Header(Version.valueOf(version), flags, streamId, Opcode.valueOf(opcode))
    Frame(header, Option(byteBuffer))
  }

  def apply(header: Header, body: Option[ByteBuffer]): Frame = {
    new Frame(header, body.fold(0)(b => b.getInt), body)
  }
}

class Frame(val header: Header, val length: Int, val body: Option[ByteBuffer]) {
  def toByteBuffer: ByteBuffer = {
    val byteBuffer = ByteBuffer.allocate(9 + this.length)
    val headerBytes = new Array[Byte](9)
    headerBytes.update(0, header.version.value)
    headerBytes.update(1, header.flags)
    headerBytes.update(2, (this.header.streamId >>> 8).toByte)
    headerBytes.update(3, (0xff & this.header.streamId).toByte)
    headerBytes.update(4, header.opcode.value)
    headerBytes.update(5, (this.length >>> 24).toByte)
    headerBytes.update(6, (0xff & (this.length >>> 16)).toByte)
    headerBytes.update(7, (0xff & (this.length >>> 8)).toByte)
    headerBytes.update(8, (0xff & this.length).toByte)
    byteBuffer.put(headerBytes)
    body.map(byteBuffer.put)
    byteBuffer.flip()
    byteBuffer
  }

}