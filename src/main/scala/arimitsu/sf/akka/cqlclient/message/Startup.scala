package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.messages._
import arimitsu.sf.cql.v3.{Flags, Compression, Opcode, Frame}
import arimitsu.sf.cql.v3.messages.Error
import arimitsu.sf.cql.v3.messages.Ready
import arimitsu.sf.cql.v3.messages.Authenticate
import java.nio.ByteBuffer

/**
 * Created by sxend on 14/06/08.
 */
case class Startup(flags: Flags, options:Map[String,String], promise: Promise[Either[Authenticate, Ready]]) extends Message {
  override def apply(frame: Frame): Unit = {
    val buffer  = ByteBuffer.wrap(frame.body)
    val result = frame.header.opcode match {
      case Opcode.READY => Right(Ready.ReadyParser.parse(buffer))
      case Opcode.AUTHENTICATE => Left(Authenticate.AuthenticateParser.parse(buffer))
    }
    promise.success(result)
  }

  override def error(e: Error): Unit = {
    promise.failure(e.toThrowable)
  }
}
