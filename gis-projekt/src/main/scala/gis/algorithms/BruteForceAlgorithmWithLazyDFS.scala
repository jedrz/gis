package gis.algorithms

import gis.LazyDFS.toDFS
import gis._

class BruteForceAlgorithmWithLazyDFS(val graph: Graph) extends GraphConnectivity {

  def pathNotExists(from: Vertex, to: Vertex): Boolean = {
    !graph.dfs(from).contains(to)
  }

  def pathsNotExist(vertex: Vertex): Boolean = {
    graph.vertices.exists(pathNotExists(vertex, _))
  }

  override def isPartiallyConnected: Boolean = {
    !graph.vertices.exists(pathsNotExist)
  }

}
