package gis.shared.algorithms.other

import gis.shared.LazyDFS.toDFS
import gis.shared._
import gis.shared.utils.ClassNameToString

class LazyDFSBasedGraphConnectivity(val graph: Graph) extends ClassNameToString {

  def isSomehowConnected: Boolean = {
    isSomehowConnected(Nil, graph.vertices)
  }

  private def isSomehowConnected(connected: List[Vertex], notConnected: List[Vertex]): Boolean = {
    notConnected match {
      case firstNotConnected :: restNotConnected =>
        val visited = graph.dfs(firstNotConnected).toList
        if (connected.toSet subsetOf visited.toSet) {
          val notConnectedAfter = restNotConnected.toSet -- visited
          isSomehowConnected(visited, notConnectedAfter.toList)
        } else {
          false
        }
      case Nil => true
    }
  }

  def solve: (List[Vertex], Boolean) = {
    solve(Nil, graph.vertices)
  }

  private def solve(connected: List[Vertex], notConnected: List[Vertex]): Solution = {
    notConnected match {
      case firstNotConnected :: restNotConnected =>
        val visited = graph.dfs(firstNotConnected).toList
        if (connected.toSet subsetOf visited.toSet) {
          val notConnectedAfter = restNotConnected.toSet -- visited
          solve(visited, notConnectedAfter.toList)
        } else {
          ((connected ++ visited).distinct, false)
        }
      case Nil => (connected, true)
    }
  }
}
