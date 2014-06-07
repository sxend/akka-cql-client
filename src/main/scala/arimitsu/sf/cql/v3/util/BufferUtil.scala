package arimitsu.sf.cql.v3.util

import java.nio.ByteBuffer

/**
 * Created by sxend on 14/06/07.
 */
object BufferUtil {

  def parseStringMap(buffer: ByteBuffer): Map[String, String] = {
    (0 until buffer.getShort).map {
      _ => (getString(buffer), getString(buffer))
    }.toMap

  }

  def parseStringList(buffer: ByteBuffer): List[String] = {
    (0 until buffer.getShort).map {
      _ => getString(buffer)
    }.toList

  }

  def parseStringMultimap(buffer: ByteBuffer): Map[String, List[String]] = {
    (0 until buffer.getShort).map {
      _ => (getString(buffer), parseStringList(buffer))
    }.toMap
  }

  def getString(buffer: ByteBuffer): String = {
    getString(buffer, buffer.getShort)
  }

  def toString(buffer: ByteBuffer): String = {
    getString(buffer, (buffer.limit() - buffer.position()).toShort)
  }

  def getString(buffer: ByteBuffer, length: Short): String = {
    val bytes = new Array[Byte](length)
    buffer.get(bytes)
    new String(bytes)
  }

}
