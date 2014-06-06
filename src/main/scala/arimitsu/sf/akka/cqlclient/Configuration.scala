package arimitsu.sf.akka.cqlclient

import java.net.InetSocketAddress
import arimitsu.sf.akka.cqlclient.events.EventCallback

/**
 * Created by sxend on 2014/06/06.
 */
case class Configuration(clusterAddress: Seq[InetSocketAddress],
                         connectionPerHost: Int)
