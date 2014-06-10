package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.{Flags, Frame}
import arimitsu.sf.cql.v3.messages.{Error, Supported}
import java.nio.ByteBuffer

/**
 * Created by sxend on 2014/06/06.
 */

case class Options(flags: Flags, promise: Promise[Supported]) extends Message {
  def apply(frame: Frame) = {
    val s = Supported.SupportedParser.parse(ByteBuffer.wrap(frame.body))
    promise.success(s)
  }

  override def error(e: Error): Unit = {
    promise.failure(e.toThrowable)
  }
}