package gis.algorithms

import gis.Graph

trait ConsistenceCheckingAlgorithm {
  def isPartiallyConsistent(graph: Graph) : Boolean
}
