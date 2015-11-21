name := "gis"

scalastyleFailOnError := true

lazy val root = project.in(file(".")).
  aggregate(gisJS, gisJVM).
  settings(
    publish := {},
    publishLocal := {}
  )

lazy val gis = crossProject.in(file(".")).
  settings(
    scalaVersion := "2.11.6",
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.4"
  ).
  jvmSettings(
    libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test",
    libraryDependencies += "com.github.monkeysintown" % "jgraphx" % "3.3.1.1",
    libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.7",
    testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework"),
    parallelExecution in Test := false
  ).
  jsSettings(
  )

scalacOptions += "-target:jvm-1.8"
scalacOptions += "-source:jvm-1.8"

skip in packageJSDependencies := false

lazy val gisJVM = gis.jvm
lazy val gisJS = gis.js