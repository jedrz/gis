package gis

class BruteForceAlgorithmSpec extends UnitSpec {

  "vertex" should "be reachable" in {
    val algorithm = new BruteForceAlgorithm

    val result = algorithm.reachable(1, 6, inconsistentGraph)

    result shouldBe true
  }

  "vertex" should "not be reachable" in {
    val algorithm = new BruteForceAlgorithm

    val result = algorithm.reachable(7, 6, inconsistentGraph)

    result shouldBe false
  }

  "path" should "exist" in {
    val algorithm = new BruteForceAlgorithm

    val result = algorithm.pathExists(1, 5, inconsistentGraph)

    result shouldBe true
  }

  "path" should "not exist" in {
    val algorithm = new BruteForceAlgorithm

    val result = algorithm.pathExists(7, 1, inconsistentGraph)

    result shouldBe false
  }

  "paths" should "exist" in {
    val algorithm = new BruteForceAlgorithm

    val result = algorithm.pathsExist(1, inconsistentGraph)

    result shouldBe true
  }

  "paths" should "not exist" in {
    val algorithm = new BruteForceAlgorithm

    val result = algorithm.pathsExist(9, inconsistentGraph)

    result shouldBe false
  }

  "graph" should "be not partially consistent" in {
    val algorithm = new BruteForceAlgorithm

    val result = algorithm.isPartiallyConsistent(inconsistentGraph)

    result shouldBe false
  }


  private def inconsistentGraph: Graph = {
    var graph = new Graph
    (1 to 11).toList.foreach((v) => graph = graph.addVertex(v))
    graph = graph
      .addEdge(1, 8)
      .addEdge(1, 9)
      .addEdge(2, 3)
      .addEdge(3, 7)
      .addEdge(4, 5)
      .addEdge(7, 4)
      .addEdge(8, 2)
      .addEdge(9, 10)
      .addEdge(10, 11)
      .addEdge(11, 6)
    graph
  }
}
