name := """scala-play"""
organization := "dev.katsukiniwa"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.4.0"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.1" % Test
libraryDependencies ++= Seq(
  "org.playframework" %% "play-slick" % "6.1.0",
  "org.playframework" %% "play-slick-evolutions" % "6.1.1",
  "org.postgresql" % "postgresql" % "42.7.4",
  "io.lemonlabs" %% "scala-uri" % "4.0.3"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "dev.katsukiniwa.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "dev.katsukiniwa.binders._"
