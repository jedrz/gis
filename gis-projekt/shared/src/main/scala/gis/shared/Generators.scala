package gis.shared

import org.scalacheck.Gen

object Generators {

  val empty: Gen[Graph] = {
    val sizeGen = Gen.choose(2, 7)
    sizeGen.map(size =>
      (1 to size).foldLeft(new Graph())(
        (graph, _) => graph.newVertex._1
      )
    )
  }

  val complete: Gen[Graph] = {
    empty.map(emptyGraph => {
      val verticesList = emptyGraph.vertices
      val edgesList = for {
        from <- verticesList
        to <- verticesList
        if from != to
      } yield (from, to)
      edgesList.foldLeft(emptyGraph) {
        case (graph, (from, to)) =>
          graph.withEdge(from, to)
      }
    })
  }

  // Kn --> Km
  // Kn <-- Km
  val twoCompleteJoinedWithOneEdge: Gen[Graph] = {
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

  val partiallyConnected: Gen[Graph] = {
    Gen.oneOf(complete, twoCompleteJoinedWithOneEdge)
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
    Gen.oneOf(empty, partiallyConnectedJoinedWithVertex)
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
