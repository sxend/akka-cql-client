package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.messages.Ready
import arimitsu.sf.cql.v3.Frame
import java.nio.ByteBuffer

/**
 * Created by sxend on 2014/06/11.
 */
case class Register(events: List[String], promise: Promise[Ready]) extends Message(promise) {
  override def process(frame: Frame): Unit = {
    val ready = Ready.fromBuffer(ByteBuffer.wrap(frame.body))
    promise.success(ready)
  }
}
