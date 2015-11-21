package gis.shared.algorithms

import gis.shared.LazyDFS.toDFS
import gis.shared.{Graph, Vertex}

/**
  * Algorytm zaimplementowany z bezpośrednio z definicji problemu.
  *
  * Sprawdza, czy dla każdej pary wierzchołków istnieje ścieżka w jedną lub drugą stronę.
  */
class SimpleAlgorithm(val graph: Graph) extends GraphConnectivity {

  override def isPartiallyConnected: Boolean = {
    val pairs = allVertexPairCombinations
    pairs forall { case (from, to) =>
      graph.dfs(from).contains(to) || graph.dfs(to).contains(from)
    }
  }

  // visible for testing
  def allVertexPairCombinations: List[(Vertex, Vertex)] = {
    graph
      .vertices
      .combinations(2)
      .map(combList => (combList(0), combList(1)))
      .toList
  }

}
