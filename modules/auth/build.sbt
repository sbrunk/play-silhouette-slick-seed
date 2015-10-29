Common.settings
DB.settings

libraryDependencies ++= Seq(
  evolutions,
  "com.adrianhurt"    %% "play-bootstrap3"         % "0.4.4-P24" withSources(),
  "net.codingwell"    %% "scala-guice"             % "4.0.0" withSources(),
  "net.ceedubs"       %% "ficus"                   % "1.1.2" withSources(),
  specs2 % Test,
  "com.mohiva"        %% "play-silhouette-testkit" % "3.0.0" % "test" withSources()
)
