package gis

class LazyDFS(val graph: Graph) {

  def dfs(vertex: Vertex): Stream[Vertex] = {
    dfs(vertex, Set.empty)
  }

  private def dfs(vertex: Vertex, visited: Set[Vertex]): Stream[Vertex] = {
    if (visited.contains(vertex)) {
      Stream.empty
    } else {
      dfsForNotVisited(vertex, visited)
    }
  }

  private def dfsForNotVisited(vertex: Vertex, visited: Set[Vertex]): Stream[Vertex] = {
    val visitedWithVertex = visited + vertex
    val adjacentNotVisited = graph.adjacencyLists(vertex).filterNot(visitedWithVertex.contains)
    lazy val dfsStream = adjacentNotVisited.toStream.flatMap(adjVertex => dfs(adjVertex, visitedWithVertex))
    vertex #:: dfsStream
  }
}

object LazyDFS {
  implicit def toDFS(graph: Graph): LazyDFS = new LazyDFS(graph)
}
