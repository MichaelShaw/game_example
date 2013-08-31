name := "game example"

version := "1.0"

organization := "Michael Shaw"

compileOrder := CompileOrder.JavaThenScala

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-unchecked", "-deprecation") // , "-optimise"

javaOptions ++= Seq("-Xmx1G","-Xms1G", "-server")

javaOptions ++= Seq("-XX:+AggressiveOpts")

javaOptions ++= Seq("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005")

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Central" at "http://repo2.maven.org/maven2"

resolvers += "Guicey" at "http://guiceyfruit.googlecode.com/svn/repo/releases/"

resolvers += Resolver.sonatypeRepo("snapshots")

fork := true

retrieveManaged := true