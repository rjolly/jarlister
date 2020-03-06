name := "jarlister"

organization := "com.github.rjolly"

description := "A tool that lists a jar's classes into its manifest"

licenses := Seq( "GPL" -> url( "http://www.gnu.org/licenses/gpl.txt" ))

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots") 
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

pomExtra :=
  <url>http://github.com/rjolly/jarlister</url>
  <scm>
    <url>git@github.com:rjolly/jarlister.git</url>
    <connection>scm:git:git@github.com:rjolly/jarlister.git</connection>
  </scm>
  <developers>
    <developer>
      <id>rjolly</id>
      <name>Raphael Jolly</name>
      <url>http://github.com/rjolly</url>
    </developer>
  </developers>

version := "1.1"

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-language:implicitConversions")

libraryDependencies += "org.apache.ant" % "ant" % "1.9.4"

mainClass in (Compile, packageBin) := Some("scala.tools.nsc.JarLister")

mainClass in (Compile, run) := Some("scala.tools.nsc.JarLister")
