package gis.shared.algorithms

import gis.shared.utils.ClassNameToString
import gis.shared.{Graph, Vertex}

class BruteForceAlgorithm(val graph: Graph) extends ClassNameToString {
  var visitedVertices = Set[Vertex]()

  def reachable(current: Vertex, to: Vertex): Boolean = {
    visitedVertices = visitedVertices + current
    if (current == to) {
      true
    } else {
      val adjacencyList = graph.adjacencyLists(current).filterNot(visitedVertices.contains)
      adjacencyList.exists(reachable(_, to))
    }
  }

  def pathNotExists(from: Vertex, to: Vertex): Boolean = {
    visitedVertices = Set[Vertex]()
    !reachable(from, to)
  }

  def pathsNotExist(vertex: Vertex): Boolean = {
    graph.vertices.exists(pathNotExists(vertex, _))
  }

  def isConnected: Boolean = {
    !graph.vertices.exists(pathsNotExist)
  }

}
