name := "dbsc"

version := "0.1"

scalaVersion := "2.13.3"

//assemblyJarName in assembly := "dbsc-1.0.jar"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml
libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-xml" % "2.11.3"

// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-scala
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.11.3"

// https://mvnrepository.com/artifact/org.scala-lang/scala-library
//libraryDependencies += "org.scala-lang" % "scala-library" % "2.13.3"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}