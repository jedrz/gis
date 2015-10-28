package gis.algorithms

import gis._

class DFSBasedGraphConnectivity(val graph: Graph) extends GraphConnectivity {

  override def isPartiallyConnected: Boolean = {
    isPartiallyConnected(Set(), graph.vertices)
  }

  private def isPartiallyConnected(connected: Set[Vertex], notConnected: List[Vertex]): Boolean = {
    notConnected match {
      case firstNotConnected :: restNotConnected =>
        val visited = dfs(firstNotConnected)
        if (connected subsetOf visited) {
          val notConnectedAfter = restNotConnected.toSet -- visited
          isPartiallyConnected(visited, notConnectedAfter.toList)
        } else {
          false
        }
      case Nil => true
    }
  }

  private def dfs(vertex: Vertex, visited: Set[Vertex] = Set()): Set[Vertex] = {
    val visitedWithVertex = visited + vertex
    val adjacentNotVisited = graph.adjacencyLists(vertex).filterNot(visitedWithVertex.contains)
    if (adjacentNotVisited.isEmpty) {
      visitedWithVertex
    } else {
      adjacentNotVisited.foldLeft(visitedWithVertex)(
        (currentVisited, adjVertex) => dfs(adjVertex, currentVisited))
    }
  }
}

object DFSBasedGraphConnectivity {
  implicit def toGraphConnectivity(graph: Graph): DFSBasedGraphConnectivity = new DFSBasedGraphConnectivity(graph)
}
