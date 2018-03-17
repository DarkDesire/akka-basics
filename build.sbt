lazy val Versions = new {
  val akka = "2.5.9"
  val http = "10.0.11"
  val config = "1.3.1"
  val logback = "1.2.3"
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
      "com.typesafe.akka" %% "akka-slf4j" % Versions.akka,
      "com.typesafe" % "config" % Versions.config,
      "com.typesafe.akka" %% "akka-http" % Versions.http,
      "com.typesafe.akka" %% "akka-http-testkit" % Versions.http % Test,
      "com.typesafe.akka" %% "akka-actor" % Versions.akka,
      "com.typesafe.akka" %% "akka-testkit" % Versions.akka % Test,
      "com.typesafe.akka" %% "akka-stream" % Versions.akka,
      "com.typesafe.akka" %% "akka-stream-testkit" % Versions.akka % Test
    )
  )