package test.akka.cql.client

import akka.actor.ActorSystem
import java.net.InetSocketAddress
import arimitsu.sf.akka.cqlclient.Cluster
import arimitsu.sf.cql.v3.{Compression, Flags}
import arimitsu.sf.cql.v3.messages.{ColumnType, Result, Query, QueryParameters}
import arimitsu.sf.cql.v3.messages.QueryParameters.ListValues
import arimitsu.sf.cql.v3.messages.results.Rows
import arimitsu.sf.cql.v3.messages.ColumnType.{ColumnTypeEnum, SetType, MapType, ListType}
import arimitsu.sf.cql.v3.messages.ColumnType.ColumnTypeEnum._
import scala.util.Failure
import arimitsu.sf.akka.cqlclient.Configuration
import scala.util.Success
import arimitsu.sf.cql.v3.util.Notation
import java.nio.ByteBuffer

/**
 * Created by sxend on 14/05/31.
 */
object Main {
  def main(args: Array[String]): Unit = {
    println(System.currentTimeMillis())
    implicit val serverSystem = ActorSystem("serverSystem")
    val configuration = Configuration(
      Seq(new InetSocketAddress("127.0.0.1", 9042)),
      Flags.COMPRESSION, Compression.LZ4, 1)
    val cluster = Cluster(configuration)
    println(System.currentTimeMillis())
    //    import serverSystem.dispatcher
    //    cluster.startup {
    //       a => a.right
    //    }.runWith {
    //      client =>
    //        val result = client.query("select * from test.test_table;", new QueryParameters(ALL, Query.QueryFlags.VALUES.mask, new ListValues(), 1, new Array[Byte](1), ALL, System.currentTimeMillis()))
    //        result.onComplete{
    //          case Success(s)=> println(s)
    //          case Failure(t) => t.printStackTrace()
    //        }
    //    }
    cluster.runWith {
      client =>
        import serverSystem.dispatcher
        client.options()
          .flatMap {
          r =>
            println(System.currentTimeMillis())
            client.startup()
        }.flatMap {
          r =>
            println(System.currentTimeMillis())
            import arimitsu.sf.cql.v3.Consistency._
            val queryParam = new QueryParameters(ALL, Query.QueryFlags.VALUES.mask, new ListValues(), 1, new Array[Byte](1), ALL, System.currentTimeMillis())
            client.query("select * from test.test_table1;", queryParam)
        }.onComplete {
          case Success(a) =>
            println(System.currentTimeMillis())
            println(a)
            a.getKind match {
              case Result.Kind.ROWS =>
                import scala.collection.JavaConversions._
                val rows = a.asInstanceOf[Rows]

            }
          case Failure(t) => t.printStackTrace()
        }

    }
  }
}

