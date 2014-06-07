package arimitsu.sf.cql.v3

/**
 * Created by sxend on 14/06/04.
 */
sealed abstract class Consistency(val value: Short)

object Consistency {

  case object ANY extends Consistency(0x0000)

  case object ONE extends Consistency(0x0001)

  case object TWO extends Consistency(0x0002)

  case object THREE extends Consistency(0x0003)

  case object QUORUM extends Consistency(0x0004)

  case object ALL extends Consistency(0x0005)

  case object LOCAL_QUORUM extends Consistency(0x0006)

  case object EACH_QUORUM extends Consistency(0x0007)

  case object SERIAL extends Consistency(0x0008)

  case object LOCAL_SERIAL extends Consistency(0x0009)

  case object LOCAL_ONE extends Consistency(0x000A)

  val values = Seq(
    ANY,
    ONE,
    TWO,
    THREE,
    QUORUM,
    ALL,
    LOCAL_QUORUM,
    EACH_QUORUM,
    SERIAL,
    LOCAL_SERIAL,
    LOCAL_ONE
  )

  def valueOf(value: Short): Consistency = {
    values.find(_.value == value).get
  }
}
