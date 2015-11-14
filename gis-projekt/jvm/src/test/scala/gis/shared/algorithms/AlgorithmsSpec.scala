package gis.shared.algorithms

import gis.shared.{Generators, Graph, Graphs, UnitSpec}
import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks

class AlgorithmsSpec extends UnitSpec with PropertyChecks {

  val algorithmFactories: List[Graph => GraphConnectivity] = List(
    graph => new SimpleAlgorithm(graph),
    graph => new SCCBasedAlgorithm(graph)
  )

  def checkAllAlgorithmsForGraph(graph: Graph, result: Boolean): Unit = {
    algorithmFactories foreach { algFactory =>
      val alg = algFactory(graph)
      val algName = alg.toString
      withClue(s"for algorithm $algName and $graph") {
        alg.isPartiallyConnected should be (result)
      }
    }
  }

  def checkAllAlgorithmsForGraphs(graphs: List[Graph], result: Boolean): Unit = {
    graphs.foreach(graph => checkAllAlgorithmsForGraph(graph, result))
  }

  "All algorithms" should "return true for predefined partially connected graphs" in {
    checkAllAlgorithmsForGraphs(Graphs.partiallyConnectedGraphs, result = true)
  }

  it should "return false for predefined not partially connected graphs" in {
    checkAllAlgorithmsForGraphs(Graphs.notPartiallyConnectedGraphs, result = false)
  }

  def checkAllAlgorithmsForGraphGen(graphGen: Gen[Graph], result: Boolean): Unit = {
    forAll(graphGen)(graph => checkAllAlgorithmsForGraph(graph, result))
  }

  it should "return true for generated partially connected graphs" in {
    checkAllAlgorithmsForGraphGen(Generators.partiallyConnected, result = true)
  }

  it should "return false for generated not partially connected graphs" in {
    checkAllAlgorithmsForGraphGen(Generators.notPartiallyConnected, result = false)
  }

}
