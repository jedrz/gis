package gis

class Graph(val adjacencyLists: Map[Vertex, AdjacencyList]) {

  def this() = this(Map.empty)

  def withEdge(from: Vertex, to: Vertex): Graph = {
    val newAdjacencyLists = adjacencyLists + (from -> (to :: adjacencyLists.getOrElse(from, Nil)).distinct)
    new Graph(newAdjacencyLists)
  }

  def withEdgeSymetric(v1: Vertex, v2: Vertex): Graph = {
    withEdge(v1, v2).withEdge(v2, v1)
  }

  def withVertex(vertex: Vertex): Graph = {
    val newAdjacencyLists = adjacencyLists + (vertex -> Nil)
    new Graph(newAdjacencyLists)
  }

  def getVertices : VerticesList = {
    adjacencyLists.keys.toList
  }

}

