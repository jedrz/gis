package gis

class BruteForceAlgorithm extends ConsistenceCheckingAlgorithm {

  def reachable(current: Vertex, to: Vertex, graph: Graph): Boolean = {
    println(current + "  " + to + "  ")
    if (current == to) {
      true
    }
    else {
      val adjacencyList = graph.adjacencyLists(current)
      adjacencyList.nonEmpty && adjacencyList.exists(reachable(_, to, graph))
    }
  }

  def pathExists(from: Vertex, to: Vertex, graph: Graph): Boolean = {
    reachable(from, to, graph)
  }

  def pathsExist(vertex: Vertex, graph: Graph): Boolean = {
    !graph.getVertices.exists(!pathExists(vertex, _, graph))
  }

  override def isPartiallyConsistent(graph: Graph): Boolean = {
    !graph.getVertices.exists(!pathsExist(_, graph))
  }

}
