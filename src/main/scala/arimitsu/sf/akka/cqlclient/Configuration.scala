package arimitsu.sf.akka.cqlclient

import java.net.InetSocketAddress
import arimitsu.sf.cql.v3.Flags

/**
 * Created by sxend on 2014/06/06.
 */
case class Configuration(clusterAddress: Seq[InetSocketAddress],
                         flags: Flags,
                         compression: String,
                         connectionPerHost: Int)

private[cqlclient] case class NodeConfiguration(nodeAddress: InetSocketAddress,
                                                flags: Flags,
                                                compression: String)
