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
    // Zbiór odwiedzonych wierzchołków od pierwszego wywołania.
    lazy val visitedWithFrom = visited + from
    lazy val adjacentNotVisited = graph.adjacencyLists(from).filterNot(visitedWithFrom.contains)
    // Strumień odwiedzonych wierzchołków począwszy od from.
    lazy val visitedLocally: Stream[Vertex] = Stream.empty
    lazy val dfsStream = adjacentNotVisited
      .foldLeft((visitedWithFrom, visitedLocally))((visitedPair, adjVertex) => {
        val (visited, visitedLocally) = visitedPair
        val dfsForAdj = dfs(adjVertex, visited)
        (visited ++ dfsForAdj, visitedLocally #::: dfsForAdj)
      })._2
    from #:: dfsStream
  }
}

object LazyDFS {
  implicit def toDFS(graph: Graph): LazyDFS = new LazyDFS(graph)
}
