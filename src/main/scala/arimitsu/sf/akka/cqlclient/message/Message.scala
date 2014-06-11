package arimitsu.sf.akka.cqlclient.message

import arimitsu.sf.cql.v3.Frame
import scala.concurrent.Promise

/**
 * Created by sxend on 14/06/07.
 */
abstract class Message[R](promise: Promise[R]) {
  def process(frame: Frame): Unit

  def error(e: arimitsu.sf.cql.v3.messages.Error): Unit = promise.failure(e.toThrowable)
}
