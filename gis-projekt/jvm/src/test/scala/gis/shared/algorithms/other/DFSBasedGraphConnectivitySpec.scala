package gis.shared.algorithms.other

import gis.shared.{Graphs, UnitSpec}

class DFSBasedGraphConnectivitySpec extends UnitSpec {

  "connected graph" should "be somehow connected" in {
    new DFSBasedGraphConnectivity(Graphs.connectedGraph).isSomehowConnected shouldBe true
  }

  "disconnected graph" should "not be somehow connected" in {
    new DFSBasedGraphConnectivity(Graphs.disconnectedGraph).isSomehowConnected shouldBe false
  }

  "second disconnected graph" should "not be somehow connected" in {
    new DFSBasedGraphConnectivity(Graphs.secondDisconnectedGraph).isSomehowConnected shouldBe false
  }

}
