package gis.shared.algorithms.other

import gis.shared.LazyDFS.toDFS
import gis.shared.utils.ClassNameToString
import gis.shared.{Graph, Vertex}

class BruteForceAlgorithmWithLazyDFS(val graph: Graph) extends ClassNameToString {

  def pathNotExists(from: Vertex, to: Vertex): Boolean = {
    !graph.dfs(from).contains(to)
  }

  def pathsNotExist(vertex: Vertex): Boolean = {
    graph.vertices.exists(pathNotExists(vertex, _))
  }

  def isConnected: Boolean = {
    !graph.vertices.exists(pathsNotExist)
  }

}
