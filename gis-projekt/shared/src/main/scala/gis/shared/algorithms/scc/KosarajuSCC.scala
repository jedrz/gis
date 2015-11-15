package gis.shared.algorithms.scc

import gis.shared.DFSForest.toDFSForest
import gis.shared.Forest.postOrder
import gis.shared.{Graph, SCC, Vertex}

// http://www.personal.kent.edu/~rmuhamma/Algorithms/MyAlgorithms/GraphAlgor/strongComponent.htm
// https://en.wikipedia.org/wiki/Kosaraju's_algorithm
class KosarajuSCC(val graph: Graph) {

  def findSCC: SCC = {
    val sccVertices = postOrder(graph.dfsForest).reverse
    val transposed = graph.transpose
    val initialVisited = Set.empty[Vertex]
    val initialScc: SCC = Stream.empty
    sccVertices.foldLeft((initialVisited, initialScc))((pair, vertex) => {
      val (visited, scc) = pair
      if (visited(vertex)) {
        (visited, scc)
      } else {
        val visitedForDfs = transposed.dfsTree(vertex, visited).get.postOrder
        (visited ++ visitedForDfs, visitedForDfs #:: scc)
      }
    })._2
  }

}

object KosarajuSCC {
  implicit def toKosarajuSCC(graph: Graph): KosarajuSCC = {
    new KosarajuSCC(graph)
  }
}