package gis.shared.algorithms.other

import gis.shared.{Graphs, UnitSpec}

class LazyDFSBasedGraphConnectivitySpec extends UnitSpec {

  "Algorithm" should "return solution with list of visited vertices for connected graph" in {
    val graph = Graphs.connectedGraph

    val (visited, result) = new LazyDFSBasedGraphConnectivity(graph).solve

    result should be (true)
    visited should contain theSameElementsAs graph.vertices
  }

  it should "return solution with list of visited vertices for disconnected graph" in {
    val graph = Graphs.disconnectedGraph

    val (visited, result) = new LazyDFSBasedGraphConnectivity(graph).solve

    result should be (false)
    visited.toSet subsetOf graph.vertices.toSet should be (true)
    visited.size should be < graph.vertices.size
  }

}
