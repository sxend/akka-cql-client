name := "akka-cql-client"

version := "0.0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "2.3.12" % "test",
  "com.typesafe.akka" % "akka-actor_2.10" % "2.3.3",
  "net.jpountz.lz4" % "lz4" % "1.2.0",
  "org.xerial.snappy" % "snappy-java" % "1.1.0.1"
)