package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.Frame
import arimitsu.sf.cql.v3.messages.{SupportedParser, Error, Supported}

/**
 * Created by sxend on 2014/06/06.
 */

case class Options(promise: Promise[Supported]) extends Message {
  def apply(frame: Frame) = {
    val s = SupportedParser.parse(frame.body.get)
    promise.success(s)
  }

  override def error(e: Error): Unit = {
    promise.failure(e.toThrowable)
  }
}