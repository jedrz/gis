package gis.shared.algorithms

import gis.shared.DFSForest.toDFSForest
import gis.shared.Forest.postOrder
import gis.shared.LazyDFS.toDFS
import gis.shared._
import gis.shared.utils.ClassNameToString

class MultipleDFSBasedAlgorithm(val graph: Graph) extends GraphConnectivity with ClassNameToString {

  private def verticesToVisit: List[Vertex] = {
    val forest = graph.dfsForest
    postOrder(forest).toList
  }

  override def isPartiallyConnected: Boolean = {
    isPartiallyConnected(Set.empty, verticesToVisit)
  }

  private def isPartiallyConnected(connected: Set[Vertex], notConnected: List[Vertex]): Boolean = {
    notConnected match {
      case firstNotConnected :: restNotConnected =>
        val visited = graph.dfs(firstNotConnected)
        if (connected subsetOf visited.toSet) {
          val notConnectedAfter = restNotConnected diff visited
          isPartiallyConnected(visited.toSet, notConnectedAfter)
        } else {
          false
        }
      case Nil => true
    }
  }

  def solve: (List[Vertex], Boolean) = {
    solve(Nil, verticesToVisit)
  }

  private def solve(connected: List[Vertex], notConnected: List[Vertex]): Solution = {
    notConnected match {
      case firstNotConnected :: restNotConnected =>
        val visited = graph.dfs(firstNotConnected)
        if (connected.toSet subsetOf visited.toSet) {
          val notConnectedAfter = restNotConnected diff visited
          solve(visited.toList, notConnectedAfter)
        } else {
          ((connected ++ visited).distinct, false)
        }
      case Nil => (connected, true)
    }
  }
}
