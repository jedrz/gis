package gis.shared

import gis.shared.DFSForest.toDFSForest
import gis.shared.Forest.postOrder

class DFSForestSpec extends UnitSpec {

  "DFSForest" should "return tree for connected graph" in {
    val graph = Graphs.initGraphWithVertices(1 to 3).withEdge(1, 2).withEdge(2, 3).withEdge(1, 3)

    val forest = graph.dfsForest.toList

    forest should have size 1

    val node1 = forest.head
    node1.vertex should be (1)
    node1.subtrees should have size 2

    // Trochę słabe założenie, że tu będzie 3
    val node3 = node1.subtrees(0)
    node3.vertex should be (3)
    node3.subtrees should be (empty)

    val node2 = node1.subtrees(1)
    node2.vertex should be (2)
    node2.subtrees should be (empty)
  }

  it should "return forest for disconnected graph" in {
    val graph = Graphs.initGraphWithVertices(1 to 4).withEdge(1, 2).withEdge(3, 4)

    graph.dfsForest should have size 2
  }

  "Forest" should "return vertices in post-order" in {
    val graph = Graphs.initGraphWithVertices(1 to 4).withEdge(1, 2).withEdge(1, 3).withEdge(2, 3).withEdge(2, 4)

    val forest = graph.dfsForest

    postOrder(forest) should contain inOrder(3, 4, 2, 1)
  }

}
