package gis.shared

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

  def newVertex: (Graph, Vertex) = {
    val newVertex = vertices.sorted.lastOption.getOrElse(0) + 1
    val newGraph = withVertex(newVertex)
    (newGraph, newVertex)
  }

  def vertices: VerticesList = {
    adjacencyLists.keys.toList
  }

  def edges: EdgesList = {
    adjacencyLists
      .toList
      .flatMap { case (vertex, adjacencyList) =>
        adjacencyList.map(adjVertex => (vertex, adjVertex))
      }
  }

  override def toString: String = {
    s"Graph($adjacencyLists)"
  }
}

