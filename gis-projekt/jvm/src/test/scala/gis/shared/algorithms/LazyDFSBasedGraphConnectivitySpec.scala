package gis.shared.algorithms

import gis.shared.algorithms.LazyDFSBasedGraphConnectivity.withSolve
import gis.shared.{Graphs, UnitSpec}

class LazyDFSBasedGraphConnectivitySpec extends UnitSpec with Graphs {

  "Algorithm" should "return solution with list of visited vertices for connected graph" in {
    val graph = connectedGraph

    val (visited, result) = graph.solve

    result should be (true)
    visited should contain theSameElementsAs graph.vertices
  }

  it should "return solution with list of visited vertices for disconnected graph" in {
    val graph = disconnectedGraph

    val (visited, result) = graph.solve

    result should be (false)
    visited.toSet subsetOf graph.vertices.toSet should be (true)
    visited.size should be < graph.vertices.size
  }

}
