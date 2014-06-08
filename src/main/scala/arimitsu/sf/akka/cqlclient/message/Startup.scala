package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.messages._
import arimitsu.sf.cql.v3.{Compression, Opcode, Frame}
import arimitsu.sf.cql.v3.messages.Error
import arimitsu.sf.cql.v3.messages.Ready
import arimitsu.sf.cql.v3.messages.Authenticate

/**
 * Created by sxend on 14/06/08.
 */
case class Startup(compression: Option[Compression],promise: Promise[Either[Authenticate, Ready]]) extends Message {
  override def apply(frame: Frame): Unit = {
    val result = frame.header.opcode match {
      case Opcode.READY => Right(ReadyParser.parse(frame.body.get))
      case Opcode.AUTHENTICATE => Left(AuthenticateParser.parse(frame.body.get))
    }
    promise.success(result)
  }

  override def error(e: Error): Unit = {
    promise.failure(e.toThrowable)
  }
}
