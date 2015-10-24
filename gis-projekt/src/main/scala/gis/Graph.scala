package gis

class Graph(val adjacencyLists: Map[Vertex, AdjacencyList]) {

  def this() = this(Map.empty)

  def addEdge(from: Vertex, to: Vertex): Graph = {
    val newAdjacencyLists = adjacencyLists + (from -> (to :: adjacencyLists.getOrElse(from, Nil)).distinct)
    new Graph(newAdjacencyLists)
  }

  def addEdgeSymetric(v1: Vertex, v2: Vertex): Graph = {
    addEdge(v1, v2).addEdge(v2, v1)
  }

  def addVertex(vertex: Vertex): Graph = {
    val newAdjacencyLists = adjacencyLists + (vertex -> Nil)
    new Graph(newAdjacencyLists)
  }

  def getVertices : VerticesList = {
    adjacencyLists.keys.toList
  }

}

