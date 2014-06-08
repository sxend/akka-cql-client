package arimitsu.sf.cql.v3.util

import java.nio.ByteBuffer

/**
 * Created by sxend on 14/06/08.
 */
object BufferUtil {
  def length(buffer: ByteBuffer): Int = {
    buffer.limit() - buffer.position()
  }
}
