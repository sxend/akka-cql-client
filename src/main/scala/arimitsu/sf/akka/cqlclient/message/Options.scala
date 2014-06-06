package arimitsu.sf.akka.cqlclient.message

import scala.concurrent.Promise
import arimitsu.sf.cql.v3.Frame

/**
 * Created by sxend on 2014/06/06.
 */

case class Options(promise: Promise[Supported]) {
  def apply(frame: Frame) = ???
}