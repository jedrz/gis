package gis

class BruteForceAlgorithmSpec extends UnitSpec {

  "vertex" should "be reachable" in {
    val graph = getGraph
    val algorithm = new BruteForceAlgorithm
    algorithm.reachable(1, 3, graph) shouldBe true
  }

  "vertex" should "not be reachable" in {
    val graph = getGraph
    val algorithm = new BruteForceAlgorithm
    algorithm.reachable(1, 4, graph) shouldBe false
  }

  private def getGraph: Graph = {
    var graph = new Graph
    graph = graph
      .addVertex(1)
      .addVertex(2)
      .addVertex(3)
      .addEdge(1, 2)
      .addEdge(2, 3)
      .addVertex(4)
    graph
  }
}
