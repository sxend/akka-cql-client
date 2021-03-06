package arimitsu.sf.akka.cqlclient.message

import arimitsu.sf.cql.v3.messages.Result.Kind
import scala.concurrent.Promise
import arimitsu.sf.cql.v3.Frame
import arimitsu.sf.cql.v3.messages.{Prepared, Result}
import java.nio.ByteBuffer

/**
 * Created by sxend on 2014/06/11.
 */
case class Prepare(query: String, promise: Promise[Prepared]) extends Message(promise) {

  override def process(frame: Frame): Unit = {
    val result = Result.Factory.fromBuffer(ByteBuffer.wrap(frame.body))
    result.getKind match {
      case Kind.PREPARED => promise.success(result.asInstanceOf[Prepared])
      case _ => promise.failure(new RuntimeException("invalid prepare response"))
    }
  }


}
