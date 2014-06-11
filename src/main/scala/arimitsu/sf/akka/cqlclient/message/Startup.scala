package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.{Opcode, Frame}
import arimitsu.sf.cql.v3.messages.Ready
import arimitsu.sf.cql.v3.messages.Authenticate
import java.nio.ByteBuffer

/**
 * Created by sxend on 14/06/08.
 */
case class Startup(options: Map[String, String], promise: Promise[Either[Authenticate, Ready]]) extends Message(promise) {
  override def process(frame: Frame): Unit = {
    val buffer = ByteBuffer.wrap(frame.body)
    val result = frame.header.opcode match {
      case Opcode.READY => Right(Ready.ReadyParser.parse(buffer))
      case Opcode.AUTHENTICATE => Left(Authenticate.AuthenticateParser.parse(buffer))
      case _ =>
        promise.failure(new RuntimeException("invalid response opcode"))
        return
    }
    promise.success(result)
  }

}
