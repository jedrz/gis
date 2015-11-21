package gis.shared

import org.scalacheck.Gen

object Generators {

  val predefinedPartiallyConnected: Gen[Graph] = {
    Gen.oneOf(Graphs.partiallyConnectedGraphs)
  }

  private val sizeGen = Gen.choose(2, 4)

  val empty: Gen[Graph] = {
    sizeGen.map(Graphs.empty)
  }

  val complete: Gen[Graph] = {
    sizeGen.map(Graphs.complete)
  }

  // Kn --> Km
  // Kn <-- Km
  val twoCompleteJoinedWithDirectedEdge: Gen[Graph] = {
    for {
      c1 <- complete
      c2 <- complete
      fromC1 <- Gen.oneOf(c1.vertices)
      toC2 <- Gen.oneOf(c2.vertices)
    } yield {
      val addedGraph = add(c1, c2)
      val joinedGraph = addedGraph.withEdge(fromC1, vertexMapper(c1)(toC2))
      joinedGraph
    }
  }

  // PC <--> C
  def partiallyConnectedAndCompleteJoinedWithUndirectedEdge(mutuallyRecursiveFreq: Int): Gen[Graph] = {
    for {
      pc <- partiallyConnectedFactory(mutuallyRecursiveFreq)
      c <- complete
      pCVertex <- Gen.oneOf(pc.vertices)
      cVertex <- Gen.oneOf(c.vertices)
    } yield {
      val addedGraph = add(pc, c)
      val joinedGraph = addedGraph.withEdgeSymetric(pCVertex, vertexMapper(pc)(cVertex))
      joinedGraph
    }
  }

  def partiallyConnectedFactory(mutuallyRecursiveFreq: Int = 10): Gen[Graph] = {
    Gen.lzy(Gen.frequency(
      (1, predefinedPartiallyConnected),
      (1, complete),
      (1, twoCompleteJoinedWithDirectedEdge),
      (mutuallyRecursiveFreq, partiallyConnectedAndCompleteJoinedWithUndirectedEdge(mutuallyRecursiveFreq))
    ))
  }

  val partiallyConnected = partiallyConnectedFactory()

  val predefinedNotPartiallyConnected: Gen[Graph] = {
    Gen.oneOf(Graphs.notPartiallyConnectedGraphs)
  }

  // g1 --> v <-- g2
  val partiallyConnectedJoinedWithVertex: Gen[Graph] = {
    for {
      pc1 <- partiallyConnected
      pc2 <- partiallyConnected
      fromPc1 <- Gen.oneOf(pc1.vertices)
      fromPc2 <- Gen.oneOf(pc2.vertices)
    } yield {
      val addedGraph = add(pc1, pc2)
      val (withJoiningVertex, joiningVertex) = addedGraph.newVertex
      withJoiningVertex
        .withEdge(fromPc1, joiningVertex)
        .withEdge(vertexMapper(pc1)(fromPc2), joiningVertex)
    }
  }

  val notPartiallyConnected: Gen[Graph] = {
    Gen.oneOf(
      predefinedNotPartiallyConnected,
      empty,
      partiallyConnectedJoinedWithVertex
    )
  }

  private def add(g1: Graph, g2: Graph): Graph = {
    val mapper = vertexMapper(g1)(_)
    val twoGraphsWithVertices = g2.vertices.foldLeft(g1)(
      (graph, vertex) => graph.withVertex(mapper(vertex))
    )
    val twoGraphs = g2.edges.foldLeft(twoGraphsWithVertices) { case (graph, (from, to)) =>
      graph.withEdge(mapper(from), mapper(to))
    }
    twoGraphs
  }

  private def vertexMapper(graph: Graph)(vertex: Vertex): Vertex = {
    val (_, newVertex) = graph.newVertex
    vertex + newVertex - 1
  }
}
