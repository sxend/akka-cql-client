package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.messages.Result
import arimitsu.sf.cql.v3.Frame
import java.nio.ByteBuffer

/**
 * Created by sxend on 2014/06/11.
 */
case class Batch(promise: Promise[Result]) extends Message(promise) {
  override def process(frame: Frame): Unit = {
    val result = Result.Parser.parse(ByteBuffer.wrap(frame.body))
    promise.success(result)
  }
}