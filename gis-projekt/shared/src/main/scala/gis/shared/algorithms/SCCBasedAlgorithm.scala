package gis.shared.algorithms

import gis.shared.algorithms.scc.KosarajuSCC.toKosarajuSCC
import gis.shared.utils.ClassNameToString
import gis.shared.{Graph, Graphs, SCC, Vertex}

class SCCBasedAlgorithm(val graph: Graph) extends GraphConnectivity with ClassNameToString {

  override def isPartiallyConnected: Boolean = {
    val scc = graph.findSCC
    val mapping = verticesMapping(scc)
    val vertices = scc.indices
    val empty = Graphs.initGraphWithVertices(vertices)
    val mappedGraph = graph.edges.foldLeft(empty) { case (newGraph, (from, to)) =>
      newGraph.withEdge(mapping(from), mapping(to))
    }
    new SimpleAlgorithm(mappedGraph).isPartiallyConnected
  }

  def verticesMapping(scc: SCC): Map[Vertex, Vertex] = {
    scc
      .zipWithIndex
      .flatMap { case (component, newVertex) =>
        val repeatVertex = List.fill(component.size)(newVertex)
        component zip repeatVertex
      }
      .toMap
  }
}

object SCCBasedAlgorithm {
  implicit def toGraphConnectivity(graph: Graph): SCCBasedAlgorithm = {
    new SCCBasedAlgorithm(graph)
  }
}
