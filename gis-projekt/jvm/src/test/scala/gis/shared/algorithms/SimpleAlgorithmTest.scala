package gis.shared.algorithms

import gis.shared.{Graphs, UnitSpec}

class SimpleAlgorithmTest extends UnitSpec {

  it should "return all vertex pair combinations" in {
    val graph = Graphs.initGraphWithVertices(1 to 3)

    val pairs = new SimpleAlgorithm(graph).allVertexPairCombinations

    pairs should have size 3
    pairs should contain allOf(
      (1, 2),
      (1, 3),
      (2, 3))
  }

}
