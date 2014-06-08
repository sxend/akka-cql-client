package arimitsu.sf.cql.v3.messages

import java.nio.ByteBuffer
import arimitsu.sf.cql.v3.util.Notation

/**
 * Created by sxend on 14/06/07.
 */
case class Authenticate(className:String)

object AuthenticateParser extends ResponseParser[Authenticate]{
  override def parse(body: ByteBuffer): Authenticate = {
    Authenticate(Notation.getString(body))
  }
}