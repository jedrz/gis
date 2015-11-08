package gis.shared

class LazyDFS(val graph: Graph) {

  def dfs(from: Vertex): Stream[Vertex] = {
    dfs(from, Set.empty)
  }

  private def dfs(from: Vertex, visited: Set[Vertex]): Stream[Vertex] = {
    if (visited.contains(from)) {
      Stream.empty
    } else {
      dfsForNotVisited(from, visited)
    }
  }

  private def dfsForNotVisited(from: Vertex, visited: Set[Vertex]): Stream[Vertex] = {
    val visitedWithFrom = visited + from
    val adjacentNotVisited = graph.adjacencyLists(from).filterNot(visitedWithFrom.contains)
    lazy val dfsStream = adjacentNotVisited.toStream.flatMap(adjVertex => dfs(adjVertex, visitedWithFrom))
    from #:: dfsStream
  }
}

object LazyDFS {
  implicit def toDFS(graph: Graph): LazyDFS = new LazyDFS(graph)
}
