package gis.jvm

import gis.shared.Graphs
import gis.shared.algorithms.{MultipleDFSBasedAlgorithm, SCCBasedAlgorithm, SimpleAlgorithm}
import org.scalameter.api._

object PerformanceTests extends Bench.LocalTime {

  override def defaultConfig: Context = super.defaultConfig ++ Context(
    exec.minWarmupRuns -> 1,
    exec.maxWarmupRuns -> 5,
    exec.benchRuns -> 5
  )

  val sizes = Gen.range("size")(50, 300, 50)

  val completeGraphs = sizes.map(Graphs.complete)

  performance of "multiple dfs based algorithm" in {
    using(completeGraphs) in { graph =>
      new MultipleDFSBasedAlgorithm(graph).isPartiallyConnected
    }
  }

  performance of "scc based algorithm" in {
    using(completeGraphs) in { graph =>
      new SCCBasedAlgorithm(graph).isPartiallyConnected
    }
  }

  performance of "simple algorithm" in {
    using(Gen.range("simple size")(10, 50, 10).map(Graphs.complete)) in { graph =>
      new SimpleAlgorithm(graph).isPartiallyConnected
    }
  }

}
