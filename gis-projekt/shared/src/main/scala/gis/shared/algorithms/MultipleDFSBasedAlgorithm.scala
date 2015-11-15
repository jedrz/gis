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
    isPartiallyConnected(Nil, verticesToVisit)
  }

  private def isPartiallyConnected(connected: List[Vertex], notConnected: List[Vertex]): Boolean = {
    notConnected match {
      case firstNotConnected :: restNotConnected =>
        val visited = graph.dfs(firstNotConnected).toList
        if (connected.toSet subsetOf visited.toSet) {
          val notConnectedAfter = restNotConnected.toSet -- visited
          isPartiallyConnected(visited, notConnectedAfter.toList)
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
