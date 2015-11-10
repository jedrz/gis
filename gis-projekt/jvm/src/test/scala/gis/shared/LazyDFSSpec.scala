package gis.shared

import gis.shared.LazyDFS.toDFS

class LazyDFSSpec extends UnitSpec {

  "Lazy DFS" should "return only visited vertices for disconnected graph" in {
    val graph = Graphs.initGraphWithVertices(1 to 4)
      .withEdge(1, 2)
      .withEdge(2, 3)
      .withEdge(4, 3)

    val visitedFrom1 = graph.dfs(1).toList

    visitedFrom1 should have size 3
    visitedFrom1 should contain allOf(1, 2, 3)

    val visitedFrom4 = graph.dfs(4).toList

    visitedFrom4 should have size 2
    visitedFrom4 should contain allOf(3, 4)
  }

  it should "return all vertices for connected graph" in {
    val graph = Graphs.initGraphWithVertices(1 to 3)
      .withEdge(1, 2)
      .withEdge(2, 3)
      .withEdge(3, 1)

    val visited = graph.dfs(1).toList

    visited should contain theSameElementsAs graph.vertices
  }

  it should "return all vertices for predefined connected graph" in {
    val visited = Graphs.connectedGraph.dfs(5).toList

    visited should contain theSameElementsAs Graphs.connectedGraph.vertices
  }
}
