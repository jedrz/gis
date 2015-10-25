package gis.algorithms

import gis.{Graph, UnitSpec}

class BruteForceAlgorithmSpec extends UnitSpec {

  "vertex" should "be reachable" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.reachable(1, 6)

    result shouldBe true
  }

  "vertex" should "not be reachable" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.reachable(7, 6)

    result shouldBe false
  }

  "path" should "exist" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.pathNotExists(1, 5)

    result shouldBe false
  }

  "path" should "not exist" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.pathNotExists(7, 1)

    result shouldBe true
  }

  "paths" should "exist" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.pathsNotExist(1)

    result shouldBe false
  }

  "paths" should "not exist" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.pathsNotExist(9)

    result shouldBe true
  }

  "graph" should "be not partially connected" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.isPartiallyConnected(disconnectedGraph)

    result shouldBe false
  }

  "graph" should "be partially connected" in {
    val algorithm = new BruteForceAlgorithm(connectedGraph)

    val result = algorithm.isPartiallyConnected(connectedGraph)

    result shouldBe true
  }


  private def disconnectedGraph: Graph = {
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

  private def connectedGraph: Graph = {
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

  private def initGraphWithVertices(range: Range): Graph = {
    range.foldLeft(new Graph)((graph, v) => graph.withVertex(v))
  }
}
