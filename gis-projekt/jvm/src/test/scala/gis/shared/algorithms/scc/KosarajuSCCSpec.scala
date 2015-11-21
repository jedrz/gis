package gis.shared.algorithms.scc

import gis.shared.algorithms.scc.KosarajuSCC.toKosarajuSCC
import gis.shared.{Graphs, UnitSpec}

class KosarajuSCCSpec extends UnitSpec {

  it should "return SCC" in {
    val graph = Graphs.initGraphWithVertices(1 to 4)
      .withEdgeSymetric(1, 2)
      .withEdgeSymetric(3, 4)
      .withEdge(2, 3)

    val scc = graph.findSCC

    val sccNormalized = scc.map(s => s.toList.sorted)
    sccNormalized should contain allOf (List(3, 4), List(1, 2))
  }

}
