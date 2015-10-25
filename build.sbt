import scalariform.formatter.preferences._

name := "play-silhouette-slick-seed"

Common.settings

libraryDependencies ++= Seq(
  "com.mohiva" %% "play-silhouette" % "3.0.0",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "net.codingwell" %% "scala-guice" % "4.0.0",
  "net.ceedubs" %% "ficus" % "1.1.2",
  "com.adrianhurt" %% "play-bootstrap3" % "0.4.4-P24",
  "com.mohiva" %% "play-silhouette-testkit" % "3.0.0" % "test",
  specs2 % Test,
  "com.typesafe.play" %% "play-slick" % "1.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
  "com.h2database" % "h2" % "1.4.188",
  cache,
  evolutions,
  filters
)

lazy val silhouette = (project in file("modules/silhouette")).enablePlugins(PlayScala)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(silhouette)
  .aggregate(silhouette)

routesGenerator := InjectedRoutesGenerator

//********************************************************
// Scalariform settings
//********************************************************

defaultScalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(FormatXml, false)
  .setPreference(DoubleIndentClassDeclaration, false)
  .setPreference(PreserveDanglingCloseParenthesis, true)

