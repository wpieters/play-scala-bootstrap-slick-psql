enablePlugins(JavaAppPackaging, UniversalDeployPlugin, DockerPlugin, WindowsPlugin)

name := """play-scala-bootstrap-slick-psql"""

version := "0.0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

val playVersion = _root_.play.core.PlayVersion.current
val playSlickVersion = "1.1.1"
val slickVersion = "3.1.0"
val postgresVersion = "9.3-1103-jdbc41"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.webjars" %% "webjars-play" % "2.4.0-2",
  "org.webjars" %	"bootstrap" % "3.1.1-2",
  "org.webjars" % "bootswatch-superhero" % "3.3.1+2",
  "org.webjars" % "html5shiv" % "3.7.0",
  "org.webjars" % "respond" % "1.4.2",
  "com.typesafe.play" %% "play-slick" % playSlickVersion,
  "com.typesafe.play" %% "play-slick-evolutions" % playSlickVersion,
//  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.postgresql" % "postgresql" % postgresVersion,
  "jp.t2v" %% "play2-auth" % "0.14.2",
  "jp.t2v" %% "play2-auth-social" % "0.14.2",
  "jp.t2v" %% "play2-auth-test" % "0.14.2" % "test",
  "org.mindrot" % "jbcrypt" % "0.3m",
  specs2 % Test,
  "com.h2database" % "h2" % "1.4.192"
)

mappings in (Windows, packageDoc) := Seq()

mappings in Universal ++=
  (baseDirectory.value / "scripts" * "*" get) map
    (x => x -> ("./" + x.getName))

doc in Compile <<= target.map(_ / "none")

instrumentSettings

ScoverageKeys.minimumCoverage := 70

ScoverageKeys.failOnMinimumCoverage := false

ScoverageKeys.highlighting := false

publishArtifact in Test := false

parallelExecution in Test := false

routesGenerator := InjectedRoutesGenerator

maintainer := "Wynand Pieters <wynandpieters@gmail.com>"

dockerBaseImage := "anapsix/alpine-java:jre8"

dockerExposedPorts := Seq(9000)

daemonUser in Docker := "root"

// general package information (can be scoped to Windows)
packageSummary in Windows := "test-windows"
packageDescription in Windows := """Test Windows MSI."""

// wix build information
wixProductId := java.util.UUID.randomUUID().toString
wixProductUpgradeId := java.util.UUID.randomUUID().toString
