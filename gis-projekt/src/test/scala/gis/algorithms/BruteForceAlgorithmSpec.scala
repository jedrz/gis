package gis.algorithms

import gis.{Graph, UnitSpec}

class BruteForceAlgorithmSpec extends UnitSpec {

  "vertex" should "be reachable" in {
    val algorithm = new BruteForceAlgorithm(inconsistentGraph)

    val result = algorithm.reachable(1, 6)

    result shouldBe true
  }

  "vertex" should "not be reachable" in {
    val algorithm = new BruteForceAlgorithm(inconsistentGraph)

    val result = algorithm.reachable(7, 6)

    result shouldBe false
  }

  "path" should "exist" in {
    val algorithm = new BruteForceAlgorithm(inconsistentGraph)

    val result = algorithm.pathExists(1, 5)

    result shouldBe true
  }

  "path" should "not exist" in {
    val algorithm = new BruteForceAlgorithm(inconsistentGraph)

    val result = algorithm.pathExists(7, 1)

    result shouldBe false
  }

  "paths" should "exist" in {
    val algorithm = new BruteForceAlgorithm(inconsistentGraph)

    val result = algorithm.pathsNotExist(1)

    result shouldBe false
  }

  "paths" should "not exist" in {
    val algorithm = new BruteForceAlgorithm(inconsistentGraph)

    val result = algorithm.pathsNotExist(9)

    result shouldBe true
  }

  "graph" should "be not partially consistent" in {
    val algorithm = new BruteForceAlgorithm(inconsistentGraph)

    val result = algorithm.isPartiallyConsistent(inconsistentGraph)

    result shouldBe false
  }

  "graph" should "be partially consistent" in {
    val algorithm = new BruteForceAlgorithm(consistentGraph)

    val result = algorithm.isPartiallyConsistent(consistentGraph)

    result shouldBe true
  }


  private def inconsistentGraph: Graph = {
    var graph = new Graph
    (1 to 11).toList.foreach((v) => graph = graph.withVertex(v))
    graph = graph
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
    graph
  }

  private def consistentGraph: Graph = {
    var graph = new Graph
    (1 to 8).toList.foreach((v) => graph = graph.withVertex(v))
    graph = graph
      .withEdge(1, 2)
      .withEdge(1, 8)
      .withEdge(2, 3)
      .withEdge(3, 4)
      .withEdge(4, 5)
      .withEdge(5, 1)
      .withEdge(8, 7)
      .withEdge(7, 6)
      .withEdge(6, 5)
    graph
  }
}
