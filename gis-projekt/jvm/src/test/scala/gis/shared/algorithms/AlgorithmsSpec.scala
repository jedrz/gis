package gis.shared.algorithms

import gis.shared.{Generators, Graph, Graphs, UnitSpec}
import org.scalatest.prop.PropertyChecks

class AlgorithmsSpec extends UnitSpec with PropertyChecks with Graphs {

  val algorithmFactories: List[Graph => GraphConnectivity] = List(
    graph => new DFSBasedGraphConnectivity(graph),
    graph => new LazyDFSBasedGraphConnectivity(graph)
  )

  algorithmFactories foreach { algFactory =>
    val algName = algFactory(new Graph()).toString

    def checkAlgorithm(graphs: List[Graph], result: Boolean): Unit = {
      algName should s"return $result for graphs" in {
        graphs.foreach(graph =>
          algFactory(graph).isPartiallyConnected should be (result)
        )
      }
    }

    checkAlgorithm(connectedGraphs, result = true)
    checkAlgorithm(disconnectedGraphs, result = false)

    algName should "return true for partially connected graphs" in {
      forAll(Generators.partiallyConnected) { graph =>
        algFactory(graph).isPartiallyConnected should be (true)
      }
    }

    algName should "return false for not partially connected graphs" in {
      forAll(Generators.notPartiallyConnected) { graph =>
        algFactory(graph).isPartiallyConnected should be(false)
      }
    }
  }

}
