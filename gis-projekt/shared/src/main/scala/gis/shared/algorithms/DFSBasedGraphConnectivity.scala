package gis.shared.algorithms

import gis.shared.{Graph, Vertex}

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

  private def dfs(from: Vertex, visited: Set[Vertex] = Set()): Set[Vertex] = {
    val visitedWithFrom = visited + from
    val adjacentNotVisited = graph.adjacencyLists(from).filterNot(visitedWithFrom.contains)
    val visitedForAdjacent = adjacentNotVisited.flatMap(adjVertex => dfs(adjVertex, visitedWithFrom)).toSet
    visitedForAdjacent + from
  }
}

object DFSBasedGraphConnectivity {
  implicit def toGraphConnectivity(graph: Graph): DFSBasedGraphConnectivity = new DFSBasedGraphConnectivity(graph)
}
