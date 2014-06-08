package arimitsu.sf.cql.v3

/**
 * Created by sxend on 14/06/08.
 */
sealed abstract class Compression(val name: String)
object Compression {



  val BODY_KEY: Array[Byte] = "COMPRESSION".getBytes("UTF-8")

  case object LZ4 extends Compression("lz4")

  case object SNAPPY extends Compression("snappy")

  val values = Seq(LZ4, SNAPPY)

  def valueOf(name: String): Compression = {
    values.find(_.name == name).getOrElse(new Compression(name) {})
  }
}
