package gis.jvm

import gis.shared.algorithms.{MultipleDFSBasedAlgorithm, SCCBasedAlgorithm, SimpleAlgorithm}
import gis.shared.{Generators, Graph, Graphs}
import org.scalameter.api._
import org.scalameter.picklers.noPickler._
import org.scalameter.{CurveData, Persistor}

object PerformanceTests extends Bench.LocalTime {

  override def defaultConfig: Context = super.defaultConfig ++ Context(
    exec.minWarmupRuns -> 1,
    exec.maxWarmupRuns -> 5,
    exec.benchRuns -> 5
  )

  override val reporter = new LoggingReporter[Double] {
    override def report(result: CurveData[Double], persistor: Persistor): Unit = {
      super.report(result, persistor)
      for (measurement <- result.measurements) {
        measurement.params.axisData.values.foreach {
          case graph: Graph =>
            val verticesSize = graph.vertices.size
            val edgesSize = graph.edges.size
            println(s"vertices: $verticesSize, edges: $edgesSize")
            println
          case _ =>
        }
      }
    }
  }

  // Uncomment to generate plots. Benchmarks with partiallyConnectedGraph need to be commented though.
  // override lazy val reporter = ChartReporter[Double](ChartFactory.XYLine())

  val sizes = Gen.range("size")(50, 300, 50)

  val completeGraphs = sizes.map(Graphs.complete)

  lazy val partiallyConnectedGraph = Gen.single("generated partially connected") {
    val gen = Generators.partiallyConnectedFactory(750)
    Iterator
      .continually { gen.suchThat(_.vertices.size > 1500).sample }
      .dropWhile(_.isEmpty)
      .map(_.get)
      .next
  }

  performance of "multiple dfs based algorithm" in {
    using(completeGraphs) in { graph =>
      new MultipleDFSBasedAlgorithm(graph).isPartiallyConnected
    }
  }

  performance of "multiple dfs based algorithm for generated partially connected" in {
    using(partiallyConnectedGraph) in { graph =>
      new MultipleDFSBasedAlgorithm(graph).isPartiallyConnected
    }
  }

  performance of "scc based algorithm" in {
    using(completeGraphs) in { graph =>
      new SCCBasedAlgorithm(graph).isPartiallyConnected
    }
  }

  performance of "scc based algorithm for generated partially connected" in {
    using(partiallyConnectedGraph) in { graph =>
      new SCCBasedAlgorithm(graph).isPartiallyConnected
    }
  }

  performance of "simple algorithm" in {
    using(Gen.range("simple size")(10, 50, 10).map(Graphs.complete)) in { graph =>
      new SimpleAlgorithm(graph).isPartiallyConnected
    }
  }

}
