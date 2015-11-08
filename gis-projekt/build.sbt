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
    scalaVersion := "2.11.6"
  ).
  jvmSettings(
    libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
  ).
  jsSettings(
  )

lazy val gisJVM = gis.jvm
lazy val gisJS = gis.js