package arimitsu.sf.akka.cqlclient

import java.net.InetSocketAddress

/**
 * Created by sxend on 2014/06/06.
 */
case class Configuration(clusterAddress: Seq[InetSocketAddress],
                         compression: String,
                         connectionPerHost: Int)
