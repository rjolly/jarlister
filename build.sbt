name := "jarlister"

version := "1.0"

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-language:implicitConversions")

mainClass in (Compile, packageBin) := Some("scala.tools.nsc.JarLister")

mainClass in (Compile, run) := Some("scala.tools.nsc.JarLister")
