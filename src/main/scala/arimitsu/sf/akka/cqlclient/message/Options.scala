package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.Frame
import arimitsu.sf.cql.v3.messages.Supported
import java.nio.ByteBuffer

/**
 * Created by sxend on 2014/06/06.
 */

case class Options(promise: Promise[Supported]) extends Message(promise) {
  def process(frame: Frame) = {
    val s = Supported.fromBuffer(ByteBuffer.wrap(frame.body))
    promise.success(s)
  }

}