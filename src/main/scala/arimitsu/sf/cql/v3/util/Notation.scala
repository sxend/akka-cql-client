package arimitsu.sf.cql.v3.util

import java.nio.ByteBuffer
import arimitsu.sf.cql.v3.Consistency
import java.net.InetSocketAddress
import java.util.UUID

/**
 * Created by sxend on 14/06/07.
 */
object Notation {

  case class OptionNotation(id: Short, value: Option[Any])

  def getInt(buffer: ByteBuffer): Int = {
    buffer.getInt
  }

  def getLong(buffer: ByteBuffer): Long = {
    buffer.getLong
  }

  def getShort(buffer: ByteBuffer): Short = {
    buffer.getShort
  }

  def getString(buffer: ByteBuffer): String = {
    getString(buffer, buffer.getShort)
  }

  def getString(buffer: ByteBuffer, length: Int): String = {
    val bytes = new Array[Byte](length)
    buffer.get(bytes)
    new String(bytes)
  }

  def getLongString(buffer: ByteBuffer): String = {
    val length = getInt(buffer)
    getString(buffer, length)
  }

  def getUUID(buffer: ByteBuffer): UUID = {
    val bytes = new Array[Byte](16)
    buffer.get(bytes)
    UUID.nameUUIDFromBytes(bytes)
  }

  def getStringList(buffer: ByteBuffer): List[String] = {
    (0 until buffer.getShort).map {
      _ => getString(buffer)
    }.toList
  }

  def getBytes(buffer: ByteBuffer): Option[Array[Byte]] = {
    val length = getInt(buffer)
    getBytes(buffer, length)
  }

  def getShortBytes(buffer: ByteBuffer): Array[Byte] = {
    val length = getShort(buffer)
    getBytes(buffer, length).get
  }

  def getBytes(buffer: ByteBuffer, length: Int): Option[Array[Byte]] = {
    Option(
      if (length > 0) {
        val bytes = new Array[Byte](length)
        buffer.get(bytes)
        bytes
      } else {
        null
      }
    )
  }

  def getOption(optionParser: ByteBuffer => Option[Any])(buffer: ByteBuffer): OptionNotation = {
    val id = getShort(buffer)
    OptionNotation(id, optionParser(buffer))
  }

  def getOptionList(listParser: ByteBuffer => List[OptionNotation])(buffer: ByteBuffer): List[OptionNotation] = {
    listParser(buffer)
  }

  def getINet(buffer: ByteBuffer): InetSocketAddress = {
    val addrArea = new Array[Byte](buffer.get)
    buffer.get(addrArea)
    InetSocketAddress.createUnresolved(new String(addrArea), getInt(buffer))
  }

  def getConsistency(buffer: ByteBuffer): Consistency = {
    Consistency.valueOf(getShort(buffer))
  }

  def getStringMap(buffer: ByteBuffer): Map[String, String] = {
    (0 until buffer.getShort).map {
      _ => (getString(buffer), getString(buffer))
    }.toMap
  }

  def getStringMultiMap(buffer: ByteBuffer): Map[String, List[String]] = {
    (0 until buffer.getShort).map {
      _ => (getString(buffer), getStringList(buffer))
    }.toMap
  }

  def toStringMap(maps: Map[String, String]): Array[Byte] = {
    Array.concat(short2Bytes(maps.size.toShort),
      maps.map {
        v => Array.concat(toString(v._1), toString(v._2))
      }.foldLeft(Array.empty[Byte]) {
        (a, b) => Array.concat(a, b)
      })
  }

  def toString(str: String): Array[Byte] = {
    val bytes = str.getBytes("UTF-8")
    Array.concat(short2Bytes(bytes.length.toShort), bytes)
  }

  def short2Bytes(s: Short): Array[Byte] = {
    val bytes = new Array[Byte](2)
    bytes.update(0, (0xff & (s >> 8)).toByte)
    bytes.update(1, (0xff & s).toByte)
    bytes
  }
}
