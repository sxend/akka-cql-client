package arimitsu.sf.cql.v3.messages


import arimitsu.sf.cql.v3.util.Notation
import java.nio.ByteBuffer

/**
 * Created by sxend on 14/06/07.
 */
object SupportedParser extends ResponseParser[Supported] {
  override def parse(implicit body: ByteBuffer): Supported = {
    val map = Notation.getStringMultiMap
    Supported(map)
  }
}

object Supported {
  val COMPRESSION = "COMPRESSION"
  val CQL_VERSION = "CQL_VERSION"

}

case class Supported(parameters: Map[String, List[String]])
