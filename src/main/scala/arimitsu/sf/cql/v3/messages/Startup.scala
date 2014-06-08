package arimitsu.sf.cql.v3.messages

import arimitsu.sf.cql.v3._
import arimitsu.sf.cql.v3.util.{BufferUtil, Notation}
import java.nio.ByteBuffer
import scala.Some

/**
 * Created by sxend on 14/06/07.
 */
case class Startup(streamId: Short, flags: Byte, compression: Option[Compression]) extends Request {
  val default = Map(
    "CQL_VERSION" -> "3.0.0"
  )

  override def toFrame: Frame = {
    val map = compression match {
      case Some(s) => default + ("COMPRESSION" -> s.name)
      case None => default
    }
    val notationResult = Notation.toStringMap(map)
    val buffer = ByteBuffer.wrap(notationResult)
    val length = BufferUtil.length(buffer)
    Frame(Header(Version.REQUEST, flags, streamId, Opcode.STARTUP),
      length,
      Option(buffer))
  }
}
