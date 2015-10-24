package gis

trait ConsistenceCheckingAlgorithm {
  def isPartiallyConsistent(graph: Graph) : Boolean
}
