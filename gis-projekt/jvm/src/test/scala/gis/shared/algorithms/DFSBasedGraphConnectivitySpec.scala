package gis.shared.algorithms

import gis.jvm.Graphs
import gis.shared.UnitSpec
import gis.shared.algorithms.DFSBasedGraphConnectivity.toGraphConnectivity

class DFSBasedGraphConnectivitySpec extends UnitSpec with Graphs {

  "connected graph" should "be partially connected" in {
    connectedGraph.isPartiallyConnected shouldBe true
  }

  "disconnected graph" should "not be partially connected" in {
    disconnectedGraph.isPartiallyConnected shouldBe false
  }

  "second disconnected graph" should "not be partially connected" in {
    secondDisconnectedGraph.isPartiallyConnected shouldBe false
  }

}
