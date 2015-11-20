package gis.shared

object Graphs {

  def partiallyConnectedGraphs: List[Graph] = {
    List(
      connectedGraph,
      // two complete graphs joined
      new Graph()
        .withEdge(1, 2).withEdge(2, 1)
        .withEdge(3, 4).withEdge(4, 3)
        .withEdge(2, 3),
      // 0 -> 1 -> 2 -> 0, 0 -> 3 -> 4
      initGraphWithVertices(1 to 4).withEdge(0, 1).withEdge(1, 2).withEdge(2, 0).withEdge(0, 3).withEdge(3, 4)
    )
  }

  def notPartiallyConnectedGraphs: List[Graph] = {
    List(
      disconnectedGraph,
      secondDisconnectedGraph,
      // 2 <- 1 -> 3
      initGraphWithVertices(1 to 3).withEdge(1, 2).withEdge(1, 3),
      // Hard, generated graph. 9 and 10 are tricky because of no out edges.
      new Graph(Map(
        5 -> List(6, 8),
        10 -> List(),
        1 -> List(10, 2),
        6 -> List(7),
        9 -> List(),
        2 -> List(3, 1),
        7 -> List(5),
        3 -> List(4, 5),
        8 -> List(10, 9),
        4 -> List(3, 5)))
    )
  }

  def disconnectedGraph: Graph = {
    val graph = initGraphWithVertices(1 to 11)
    graph
      .withEdge(1, 8)
      .withEdge(1, 9)
      .withEdge(2, 3)
      .withEdge(3, 7)
      .withEdge(4, 5)
      .withEdge(7, 4)
      .withEdge(8, 2)
      .withEdge(9, 10)
      .withEdge(10, 11)
      .withEdge(11, 6)
  }

  // 1 -> 2 <- 3
  def secondDisconnectedGraph: Graph = {
    val graph = initGraphWithVertices(1 to 3)
    graph
      .withEdge(1, 2)
      .withEdge(3, 2)
  }

  def connectedGraph: Graph = {
    val graph = initGraphWithVertices(1 to 8)
    graph
      .withEdge(1, 2)
      .withEdge(1, 8)
      .withEdge(2, 3)
      .withEdge(3, 4)
      .withEdge(4, 5)
      .withEdge(5, 1)
      .withEdge(8, 7)
      .withEdge(7, 6)
      .withEdge(6, 5)
  }

  def initGraphWithVertices(seq: Seq[Vertex]): Graph = {
    seq.foldLeft(new Graph)((graph, v) => graph.withVertex(v))
  }
}
