package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.messages.AuthSuccess
import arimitsu.sf.cql.v3.Frame
import java.nio.ByteBuffer
import arimitsu.sf.cql.v3.messages.parser.AuthSuccessParser

/**
 * Created by sxend on 2014/06/11.
 */
case class AuthResponse(token: Array[Byte], promise: Promise[AuthSuccess]) extends Message(promise) {
  override def process(frame: Frame): Unit = {
    val authSuccess = AuthSuccessParser.INSTANCE.parse(ByteBuffer.wrap(frame.body))
    promise.success(authSuccess)
  }

}
