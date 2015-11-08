package gis.shared

import scala.collection.immutable.Nil

class GraphSpec extends UnitSpec {

  it should "add vertex" in {
    var graph = new Graph

    graph = graph.withVertex(1)

    graph.adjacencyLists(1) should be(Nil)
  }

  it should "add edge and fill proper adjacency list" in {
    var graph = new Graph

    graph = graph
      .withEdge(1, 2)
      .withEdge(1, 3)
      .withEdge(1, 5)

    graph.adjacencyLists(1) should contain theSameElementsAs List(2, 3, 5)
  }

  it should "add both edges" in {
    var graph = new Graph

    graph = graph
      .withEdgeSymetric(1, 2)

    graph.adjacencyLists(1) should contain theSameElementsAs List(2)
    graph.adjacencyLists(2) should contain theSameElementsAs List(1)
  }

  it should "not add the same vertices to the adjacency list" in {
    var graph = new Graph

    graph = graph
      .withEdgeSymetric(1, 2)
      .withEdgeSymetric(2, 1)

    graph.adjacencyLists(1) should contain theSameElementsAs List(2)
    graph.adjacencyLists(2) should contain theSameElementsAs List(1)
  }
}
