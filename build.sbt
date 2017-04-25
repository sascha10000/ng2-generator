name := "web-gen-bundle"

version := "1.0"

scalaVersion := "2.12.1"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.1"
)

lazy val core = project in file("core")
lazy val angular = (project in file("angular")).dependsOn(core).settings(commonSettings)
lazy val examples = (project in file("examples")).dependsOn(angular).settings(commonSettings)
