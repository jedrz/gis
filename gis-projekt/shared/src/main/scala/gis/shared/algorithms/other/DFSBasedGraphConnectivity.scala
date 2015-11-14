package gis.shared.algorithms.other

import gis.shared.utils.ClassNameToString
import gis.shared.{Graph, Vertex}

class DFSBasedGraphConnectivity(val graph: Graph) extends ClassNameToString {

  def isSomehowConnected: Boolean = {
    isSomehowConnected(Set(), graph.vertices)
  }

  private def isSomehowConnected(connected: Set[Vertex], notConnected: List[Vertex]): Boolean = {
    notConnected match {
      case firstNotConnected :: restNotConnected =>
        val visited = dfs(firstNotConnected).toSet
        if (connected subsetOf visited) {
          val notConnectedAfter = restNotConnected.toSet -- visited
          isSomehowConnected(visited, notConnectedAfter.toList)
        } else {
          false
        }
      case Nil => true
    }
  }

  private def dfs(from: Vertex, visited: Set[Vertex] = Set()): List[Vertex] = {
    val visitedWithFrom = visited + from
    val adjacentNotVisited = graph.adjacencyLists(from).filterNot(visitedWithFrom.contains)
    val visitedForAdjacent = adjacentNotVisited.flatMap(adjVertex => dfs(adjVertex, visitedWithFrom))
    from :: visitedForAdjacent
  }
}
