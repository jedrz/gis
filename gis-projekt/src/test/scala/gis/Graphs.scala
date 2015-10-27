package gis

trait Graphs {

  def initGraphWithVertices(range: Range): Graph = {
    range.foldLeft(new Graph)((graph, v) => graph.withVertex(v))
  }

  // Użyć scala-check, który poda listę zdefiowanych przez nas cześciowo spójnych
  // i niespójnych grafów, których będziemy używali dla wszystkich algorytmów.

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
}
