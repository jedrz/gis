package gis.shared

case class Node(vertex: Vertex, subtrees: Stream[Node]) {

  def postOrder: Stream[Vertex] = {
    subtrees.flatMap(_.postOrder) #::: Stream(vertex)
  }

  def topologicalSort: Stream[Vertex] = {
    postOrder.reverse
  }

}

// Please, don't kill me
object Forest {

  implicit def postOrder(forest: Stream[Node]): Stream[Vertex] = {
    forest.flatMap(_.postOrder)
  }

  // It's different than pre-order
  // https://en.wikipedia.org/wiki/Depth-first_search#Output_of_a_depth-first_search
  implicit def topologicalSort(forest: Stream[Node]): Stream[Vertex] = {
    postOrder(forest).reverse
  }

}

// http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.52.7441&rep=rep1&type=pdf
class DFSForest(val graph: Graph) {

  def dfsForest: Stream[Node] = {
    val initialVisited = Set.empty[Vertex]
    val initialForest = Stream.empty[Node]
    graph.vertices.foldLeft((initialVisited, initialForest))((pair, vertex) => {
      val (visited, forest) = pair
      val treeOption = dfsTree(vertex, visited)
      (visited ++ collectVisited(treeOption), forest #::: treeOption.toStream)
    })._2
  }

  def dfsTree(from: Vertex, visited: Set[Vertex] = Set.empty): Option[Node] = {
    if (visited.contains(from)) {
      None
    } else {
      dfsTreeForNotVisited(from, visited)
    }
  }

  private def dfsTreeForNotVisited(from: Vertex, visited: Set[Vertex]): Option[Node] = {
    val visitedWithFrom = visited + from
    val adjacentNotVisited = graph.adjacencyLists(from).filterNot(visitedWithFrom)
    val initialSubtrees = Stream.empty[Node]
    val subtrees = adjacentNotVisited
      .foldLeft((visitedWithFrom, initialSubtrees))((pair, adjVertex) => {
        val (visited, subtrees) = pair
        val dfsForAdj = dfsTree(adjVertex, visited)
        (visited ++ collectVisited(dfsForAdj), subtrees #::: dfsForAdj.toStream)
      }
    )
    Some(Node(from, subtrees._2))
  }

  private def collectVisited(node: Option[Node]): Set[Vertex] = {
    node
      .map { case Node(vertex, subtrees) =>
          vertex #:: subtrees.flatMap(subtree => collectVisited(Some(subtree)))
      }
      .getOrElse(Set.empty)
      .toSet
  }

}

object DFSForest {
  implicit def toDFSForest(graph: Graph): DFSForest = {
    new DFSForest(graph)
  }
}
