package gis.shared

import org.scalacheck.Gen

object Generators {

  val complete: Gen[Graph] = {
    val sizeGen = Gen.choose(2, 7)
    sizeGen.map(size => {
      val emptyGraph = (1 to size).foldLeft(new Graph())((graph, _) => graph.newVertex._1)
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

  val twoCompleteJoinedWithOneEdge: Gen[Graph] = {
    for {
      c1 <- complete
      c2 <- complete
      fromC1 <- Gen.oneOf(c1.vertices)
      toC2 <- Gen.oneOf(c2.vertices)
    } yield {
      val (_, newVertex) = c1.newVertex

      val vertexMapper = (vertex: Vertex) => vertex + newVertex - 1

      val twoCompleteGraphsWithVertices = c2.vertices.foldLeft(c1)(
        (graph, vertex) => graph.withVertex(vertexMapper(vertex))
      )
      val twoCompleteGraphs = c2.edges.foldLeft(twoCompleteGraphsWithVertices) { case (graph, (from, to)) =>
        graph.withEdge(vertexMapper(from), vertexMapper(to))
      }
      val joinedGraph = twoCompleteGraphs.withEdge(fromC1, vertexMapper(toC2))
      joinedGraph
    }
  }

  val partiallyConnected: Gen[Graph] = {
    Gen.oneOf(complete, twoCompleteJoinedWithOneEdge)
  }

  val notPartiallyConnected: Gen[Graph] = {
    Gen.const(new Graph().withVertex(1).withVertex(2))
  }
}
