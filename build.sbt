name := "play-silhouette-slick-seed"

Common.settings

lazy val admin = (project in file("modules/admin")).enablePlugins(PlayScala).dependsOn(auth)
lazy val open  = (project in file("modules/open")) .enablePlugins(PlayScala).dependsOn(auth)
lazy val auth  = (project in file("modules/auth")) .enablePlugins(PlayScala)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .aggregate(admin, open)
  .dependsOn(admin, open)

routesGenerator := InjectedRoutesGenerator

import scalariform.formatter.preferences._
defaultScalariformSettings
ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(FormatXml, false)
  .setPreference(DoubleIndentClassDeclaration, false)
  .setPreference(PreserveDanglingCloseParenthesis, true)

