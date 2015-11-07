package gis.algorithms

import gis.LazyDFS.toDFS
import gis._

class LazyDFSBasedGraphConnectivity(val graph: Graph) extends GraphConnectivity {

  override def isPartiallyConnected: Boolean = {
    isPartiallyConnected(Set(), graph.vertices)
  }

  private def isPartiallyConnected(connected: Set[Vertex], notConnected: List[Vertex]): Boolean = {
    notConnected match {
      case firstNotConnected :: restNotConnected =>
        val visited = graph.dfs(firstNotConnected).toSet
        if (connected subsetOf visited) {
          val notConnectedAfter = restNotConnected.toSet -- visited
          isPartiallyConnected(visited, notConnectedAfter.toList)
        } else {
          false
        }
      case Nil => true
    }
  }
}
