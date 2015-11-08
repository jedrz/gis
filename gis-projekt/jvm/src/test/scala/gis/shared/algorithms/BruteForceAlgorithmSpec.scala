package gis.shared.algorithms

import gis.jvm.Graphs
import gis.shared.UnitSpec

class BruteForceAlgorithmSpec extends UnitSpec with Graphs {

  "vertex" should "be reachable" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.reachable(1, 6)

    result shouldBe true
  }

  it should "not be reachable" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.reachable(7, 6)

    result shouldBe false
  }

  "path" should "exist" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.pathNotExists(1, 5)

    result shouldBe false
  }

  it should "not exist" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.pathNotExists(7, 1)

    result shouldBe true
  }

  "paths" should "exist" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.pathsNotExist(1)

    result shouldBe false
  }

  it should "not exist" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.pathsNotExist(9)

    result shouldBe true
  }

  "disconnected graph" should "not be partially connected" in {
    val algorithm = new BruteForceAlgorithm(disconnectedGraph)

    val result = algorithm.isPartiallyConnected

    result shouldBe false
  }

  "connected graph" should "be partially connected" in {
    val algorithm = new BruteForceAlgorithm(connectedGraph)

    val result = algorithm.isPartiallyConnected

    result shouldBe true
  }

  "other graph" should "not be partially connected" in {
    val algorithm = new BruteForceAlgorithm(secondDisconnectedGraph)

    val result = algorithm.isPartiallyConnected

    result shouldBe false
  }


}
