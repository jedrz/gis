package gis

package object shared {
  type Vertex = Int
  type AdjacencyList = List[Vertex]
  type VerticesList = List[Vertex]
  type EdgesList = List[(Vertex, Vertex)]
  type Solution = (List[Vertex], Boolean)
}
