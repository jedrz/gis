package gis.algorithms

import gis._

class BruteForceAlgorithm(val graph: Graph) extends ConsistenceCheckingAlgorithm {
  var visitedVertices = Set[Vertex]()

  def reachable(current: Vertex, to: Vertex): Boolean = {
    visitedVertices = visitedVertices + current
    if (current == to) {
      true
    }
    else {
      val adjacencyList = graph.adjacencyLists(current).filterNot(visitedVertices.contains)
      adjacencyList.nonEmpty && adjacencyList.exists(reachable(_, to))
    }
  }

  def pathExists(from: Vertex, to: Vertex): Boolean = {
    visitedVertices = Set[Vertex]()
    reachable(from, to)
  }

  def pathsNotExist(vertex: Vertex): Boolean = {
    graph.getVertices.exists(!pathExists(vertex, _))
  }

  def isPartiallyConsistent(graph: Graph): Boolean = {
    !graph.getVertices.exists(pathsNotExist)
  }

}
