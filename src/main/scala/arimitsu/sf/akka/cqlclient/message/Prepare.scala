package arimitsu.sf.akka.cqlclient.message

import arimitsu.sf.cql.v3.messages.Result.Kind
import scala.concurrent.Promise
import arimitsu.sf.cql.v3.Frame
import arimitsu.sf.cql.v3.messages.Result
import java.nio.ByteBuffer
import arimitsu.sf.cql.v3.messages.results.Prepared

/**
 * Created by sxend on 2014/06/11.
 */
case class Prepare(promise: Promise[Prepared]) extends Message(promise) {

  override def process(frame: Frame): Unit = {
    val result = Result.Parser.parse(ByteBuffer.wrap(frame.body))
    result.getKind match {
      case Kind.PREPARED => promise.success(result.asInstanceOf[Prepared])
      case _ => promise.failure(new RuntimeException("invalid prepare response"))
    }
  }


}
