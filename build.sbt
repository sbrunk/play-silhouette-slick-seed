import scalariform.formatter.preferences._

name := "play-silhouette-slick-seed"

Common.settings

libraryDependencies ++= Seq(
  specs2 % Test,
  "com.typesafe.play" %% "play-slick" % "1.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.1",
  "com.h2database" % "h2" % "1.4.188",
  cache
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

