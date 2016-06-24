enablePlugins(JavaAppPackaging, UniversalDeployPlugin, DockerPlugin)

name := """play-scala-tutorials"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.webjars" 			    %% 	"webjars-play" 			    % "2.4.0-2",
  "org.webjars" 			    %	  "bootstrap" 			      % "3.1.1-2",
  "org.webjars" 			    % 	"bootswatch-darkly" 	  % "3.3.1+2",
  "org.webjars" 			    % 	"html5shiv" 			      % "3.7.0",
  "org.webjars" 			    % 	"respond" 				      % "1.4.2",
  "com.typesafe.play" 		%% 	"play-slick" 			      % "1.1.1",
  "com.typesafe.play" 		%% 	"play-slick-evolutions" % "1.1.1"
)

instrumentSettings

ScoverageKeys.minimumCoverage := 70

ScoverageKeys.failOnMinimumCoverage := false

ScoverageKeys.highlighting := false

publishArtifact in Test := false

parallelExecution in Test := false

routesGenerator := InjectedRoutesGenerator

dockerBaseImage := "anapsix/alpine-java:jre8"

dockerExposedPorts := Seq(9000)

daemonUser in Docker := "root"
