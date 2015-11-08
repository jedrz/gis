package gis.shared.algorithms

import gis.jvm.Graphs
import gis.shared.{Graph, UnitSpec}

class AlgorithmsSpec extends UnitSpec with Graphs {

  val algorithmFactories: List[Graph => GraphConnectivity] = List(
    graph => new BruteForceAlgorithm(graph),
    graph => new BruteForceAlgorithmWithLazyDFS(graph),
    graph => new DFSBasedGraphConnectivity(graph),
    graph => new LazyDFSBasedGraphConnectivity(graph)
  )

  def checkAlgorithm(algFactory: Graph => GraphConnectivity, graphs: List[Graph], result: Boolean): Unit = {
    graphs.zipWithIndex foreach { case (graph, index) =>
      val algorithm = algFactory(graph)
      val testDescription = s"return $result for graph $index"
      val testFn = () => algorithm.isPartiallyConnected should be(result)
      if (index == 0) {
        algorithm.getClass.getSimpleName should testDescription in {
          testFn()
        }
      } else {
        it should testDescription in {
          testFn()
        }
      }
    }
  }

  algorithmFactories foreach { algFactory =>
    checkAlgorithm(algFactory, connectedGraphs, result = true)
    checkAlgorithm(algFactory, disconnectedGraphs, result = false)
  }

}
