package arimitsu.sf.cql.v3.messages

import java.nio.ByteBuffer

/**
 * Created by sxend on 14/06/07.
 */
case class Ready() {
}

object ReadyParser extends ResponseParser[Ready] {
  override def parse(body: ByteBuffer): Ready = {
    Ready()
  }
}