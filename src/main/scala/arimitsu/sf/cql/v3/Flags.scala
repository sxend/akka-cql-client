package arimitsu.sf.cql.v3

/**
 * Created by sxend on 14/06/04.
 */
sealed abstract class Flags(val value: Byte)

object Flags {


  case object NONE extends Flags(0x00)

  case object COMPRESSION extends Flags(0x01)

  case object TRACING extends Flags(0x02)

  case object BOTH extends Flags((COMPRESSION.value | TRACING.value).toByte)

}
