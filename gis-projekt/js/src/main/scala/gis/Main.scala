package gis

import scala.scalajs.js

object Main extends js.JSApp {
  def main(): Unit = {
    val graph = new Graph().withVertex(1).withVertex(2)
    val vertices = graph.vertices
    println(s"Hello World ScalaJS. Vertices = $vertices")
  }
}