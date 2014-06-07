package arimitsu.sf.akka.cqlclient.message

import arimitsu.sf.cql.v3.Frame

/**
 * Created by sxend on 14/06/07.
 */
trait Message {
  def apply(frame: Frame): Unit

  def error(e: arimitsu.sf.cql.v3.messages.Error): Unit
}
