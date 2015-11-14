package gis.shared.algorithms.other

import gis.shared.{Graphs, UnitSpec}

class BruteForceAlgorithmSpec extends UnitSpec {

  "vertex" should "be reachable" in {
    val algorithm = new BruteForceAlgorithm(Graphs.disconnectedGraph)

    val result = algorithm.reachable(1, 6)

    result shouldBe true
  }

  it should "not be reachable" in {
    val algorithm = new BruteForceAlgorithm(Graphs.disconnectedGraph)

    val result = algorithm.reachable(7, 6)

    result shouldBe false
  }

  "path" should "exist" in {
    val algorithm = new BruteForceAlgorithm(Graphs.disconnectedGraph)

    val result = algorithm.pathNotExists(1, 5)

    result shouldBe false
  }

  it should "not exist" in {
    val algorithm = new BruteForceAlgorithm(Graphs.disconnectedGraph)

    val result = algorithm.pathNotExists(7, 1)

    result shouldBe true
  }

  "paths" should "exist" in {
    val algorithm = new BruteForceAlgorithm(Graphs.disconnectedGraph)

    val result = algorithm.pathsNotExist(1)

    result shouldBe false
  }

  it should "not exist" in {
    val algorithm = new BruteForceAlgorithm(Graphs.disconnectedGraph)

    val result = algorithm.pathsNotExist(9)

    result shouldBe true
  }

  "disconnected graph" should "not be connected" in {
    val algorithm = new BruteForceAlgorithm(Graphs.disconnectedGraph)

    val result = algorithm.isConnected

    result shouldBe false
  }

  "connected graph" should "be connected" in {
    val algorithm = new BruteForceAlgorithm(Graphs.connectedGraph)

    val result = algorithm.isConnected

    result shouldBe true
  }

  "other graph" should "not be connected" in {
    val algorithm = new BruteForceAlgorithm(Graphs.secondDisconnectedGraph)

    val result = algorithm.isConnected

    result shouldBe false
  }


}
