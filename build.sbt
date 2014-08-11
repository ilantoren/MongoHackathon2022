name := "JEDB_sample"

version := "1.0"

scalaVersion := "2.11.1"

resolvers ++= Seq(
  "SpringSource Milestone Repository" at "http://repo.springsource.org/milestone",
  "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.4",
  "org.springframework.scala" % "spring-scala" % "1.0.0.M2",
  "javax.inject" % "javax.inject" % "1",
  "junit" % "junit" % "4.11" % "test",
  "org.specs2" % "specs2_2.11.0-RC3" % "2.3.10" % "test"  ,
  "com.novocode" % "junit-interface" % "0.9" % "test->default",
   "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.sleepycat" % "je" % "5.0.73"
)

ivyXML :=
  <dependencies>
    <dependency org="com.sleepycat" name="je" rev="5.0.73" />
  </dependencies>

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v", "-a")
