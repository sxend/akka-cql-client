package arimitsu.sf.cql.v3.message


import arimitsu.sf.cql.v3.util.BufferUtil
import java.nio.ByteBuffer

/**
 * Created by sxend on 14/06/07.
 */
object SupportedParser extends ResponseParser[Supported] {
  override def parse(body: ByteBuffer): Supported = {
    val map = BufferUtil.parseStringMultimap(body)
    Supported(map)
  }
}

object Supported {
  val COMPRESSION = "COMPRESSION"
  val CQL_VERSION = "CQL_VERSION"

}

case class Supported(parameters: Map[String, List[String]])
