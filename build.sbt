name := "JEDB_sample"

version := "1.0"

scalaVersion := "2.11.2"

resolvers ++= Seq(
  "SpringSource Milestone Repository" at "http://repo.springsource.org/milestone",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "oracleReleases" at "http://download.oracle.com/maven")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.5",
  "org.springframework.scala" % "spring-scala" % "1.0.0.M2",
  "javax.inject" % "javax.inject" % "1",
  "junit" % "junit" % "4.11" % "test",
   "org.specs2" % "specs2_2.10" % "2.2.3",
  "com.novocode" % "junit-interface" % "0.9" % "test->default",
    "com.cra.figaro" % "figaro_2.11" % "2.2.0.0",
   "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.sleepycat" % "je" % "6.1.5"
)


testOptions += Tests.Argument(TestFrameworks.JUnit, "-v", "-a")
