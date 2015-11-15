package gis.shared.algorithms.scc

import gis.shared.algorithms.scc.KosarajuSCC.toKosarajuSCC
import gis.shared.{Graphs, UnitSpec}

class KosarajuSCCSpec extends UnitSpec {

  it should "return SCC" in {
    val graph = Graphs.initGraphWithVertices(1 to 4)
      .withEdgeSymetric(1, 2)
      .withEdgeSymetric(3, 4)
      .withEdge(2, 3)

    // Trochę słabo, że wymaga kolejności.
    graph.findSCC should be (Stream(Stream(4, 3), Stream(2, 1)))
  }

}
