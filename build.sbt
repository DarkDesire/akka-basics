lazy val Versions = new {
  val akka = "2.5.9"
  val http = "10.0.11"
  val config = "1.3.1"
  val logback = "1.2.3"
  val levelDB = "1.8"
  val iq80 = "0.7"
}

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "darkDesire",
      scalaVersion := "2.12.4",
      version      := "0.0.1"
    )),
    name := "akka-basics",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % Versions.logback,
      "org.iq80.leveldb" % "leveldb" % Versions.iq80,
      "org.fusesource.leveldbjni" % "leveldbjni-all" % Versions.levelDB,
      "com.typesafe.akka" %% "akka-slf4j" % Versions.akka,
      "com.typesafe" % "config" % Versions.config,
      "com.typesafe.akka" %% "akka-http" % Versions.http,
      "com.typesafe.akka" %% "akka-http-testkit" % Versions.http % Test,
      "com.typesafe.akka" %% "akka-actor" % Versions.akka,
      "com.typesafe.akka" %% "akka-persistence" % Versions.akka,
      "com.typesafe.akka" %% "akka-testkit" % Versions.akka % Test,
      "com.typesafe.akka" %% "akka-stream" % Versions.akka,
      "com.typesafe.akka" %% "akka-stream-testkit" % Versions.akka % Test
    )
  )