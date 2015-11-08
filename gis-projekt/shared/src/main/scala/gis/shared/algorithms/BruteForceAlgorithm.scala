package gis.shared.algorithms

import gis.shared.{Graph, Vertex}

class BruteForceAlgorithm(val graph: Graph) extends GraphConnectivity {
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

  override def isPartiallyConnected: Boolean = {
    !graph.vertices.exists(pathsNotExist)
  }

}
