package gis.shared.algorithms

import gis.shared.algorithms.DFSBasedGraphConnectivity.toGraphConnectivity
import gis.shared.{Graphs, UnitSpec}

class DFSBasedGraphConnectivitySpec extends UnitSpec {

  "connected graph" should "be partially connected" in {
    Graphs.connectedGraph.isPartiallyConnected shouldBe true
  }

  "disconnected graph" should "not be partially connected" in {
    Graphs.disconnectedGraph.isPartiallyConnected shouldBe false
  }

  "second disconnected graph" should "not be partially connected" in {
    Graphs.secondDisconnectedGraph.isPartiallyConnected shouldBe false
  }

}
