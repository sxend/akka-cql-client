package test.akka.cql.client

import akka.actor.ActorSystem
import java.net.InetSocketAddress
import arimitsu.sf.akka.cqlclient.Cluster
import arimitsu.sf.cql.v3.{Compression, Flags}
import arimitsu.sf.cql.v3.messages.{Result, Query, QueryParameters}
import arimitsu.sf.cql.v3.messages.QueryParameters.ListValues
import arimitsu.sf.cql.v3.messages.results.Rows
import arimitsu.sf.cql.v3.messages.ColumnType.{SetType, MapType, ListType}
import arimitsu.sf.cql.v3.messages.ColumnType.ColumnTypeEnum._
import scala.util.Failure
import arimitsu.sf.akka.cqlclient.Configuration
import scala.util.Success
import arimitsu.sf.cql.v3.util.Notation

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
                val columnSpec = rows.metadata.columnSpec
                for ((r, i) <- rows.rowsContent.zipWithIndex; spec <- columnSpec) {
                  val value = r.get(spec.columnName)
                  spec.columnType match {
                    case CUSTOM =>

                    case ASCII =>
                      println(new String(value))
                    case BIGINT =>
                      println(value.length)
                      println(Notation.toInt(value))
                    case BLOB =>
                      value
                    case BOOLEAN =>
                      value
                    case COUNTER =>
                    case DECIMAL =>
                      value
                    case DOUBLE =>
                      value
                    case FLOAT =>
                      value
                    case INT =>
                      value
                    case TIMESTAMP =>
                      value
                    case UUID =>
                      println(java.util.UUID.nameUUIDFromBytes(value).toString)
                    case VARCHAR =>
                      new String(value)
                    case VARINT =>
                    case TIMEUUID =>
                      println(java.util.UUID.nameUUIDFromBytes(value).toString)
                    case INET =>
                      val inet =  new InetSocketAddress(new String(value),10)
                      println(inet)
                    case UDT =>
                    case l: ListType =>
                    case m: MapType =>
                    case s: SetType =>
                  }
                }
            }
          case Failure(t) => t.printStackTrace()
        }

    }
  }
}

