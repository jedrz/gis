package gis.algorithms

import gis.Graph

trait ConnectivityCheckingAlgorithm {
  def isPartiallyConnected(graph: Graph): Boolean
}
