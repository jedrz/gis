package gis.jvm

import java.awt.{Dimension, GridBagConstraints}
import java.util
import javax.swing.{BorderFactory, JFrame}

import com.mxgraph.layout.mxFastOrganicLayout
import com.mxgraph.model.mxCell
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxConstants._
import com.mxgraph.view.mxGraph
import gis.shared.{AdjacencyList, Graph, Solution, Vertex}

class GraphVisualization(newJFrame: JFrame) {
  val jFrame: JFrame = newJFrame
  val graphView: mxGraph = new mxGraph
  var vertexMap: Map[Int, mxCell] = Map.empty
  var solution: Solution = (Nil, false)
  var stepNo: Int = 1

  def addVertex(vertex: Vertex): Unit = {
    val addedVertex = graphView.insertVertex(graphView.getDefaultParent, vertex.toString, vertex, 1, 1, 50, 50, "NOT_VISITED")
    vertexMap += (vertex -> addedVertex.asInstanceOf[mxCell])
  }

  def addEdge(from: Vertex, to: Vertex): Unit = {
    graphView.insertEdge(graphView.getDefaultParent, from + "-" + to, "", vertexMap.get(from).orNull, vertexMap.get(to).orNull)
  }

  def addEdges(edges: (Vertex, AdjacencyList)): Unit = {
    edges._2.foreach(addEdge(edges._1, _))
  }

  def visualizeGraph(graph: Graph): Unit = {
    setUpVertexStyles()
    graphView.getModel.beginUpdate()
    graph.vertices.foreach(addVertex)
    graph.adjacencyLists.foreach(addEdges)
    graphView.getModel.endUpdate()
    graphView.setCellsSelectable(false)
    val graphComponent = new mxGraphComponent(graphView)
    graphComponent.setBorder(BorderFactory.createEmptyBorder())
    graphComponent.setPreferredSize(new Dimension(1000, 800))
    graphComponent.setMinimumSize(new Dimension(1000, 800))
    graphComponent.setFocusable(false)
    graphComponent.setConnectable(false)
    graphComponent.getLayout
    val myLayout = new mxFastOrganicLayout(graphView)
    myLayout.setUseBoundingBox(false)
    myLayout.execute(graphView.getDefaultParent)
    jFrame.getContentPane.add(graphComponent, new GridBagConstraints())
  }

  def visualize(solution: Solution, graph: Graph): Unit = {
    this.solution = solution
    visualizeGraph(graph)
    graphView.setAutoSizeCells(true)
    graphView.setCellsLocked(true)
  }

  def nextStep: Boolean = {
    solution._1 match {
      case nextVertex :: vertices =>
        val visualizedVertex = vertexMap(nextVertex)
        visualizedVertex.setValue(visualizedVertex.getValue + "/" + stepNo)
        stepNo += 1
        graphView.setCellStyle("VISITED", Array(visualizedVertex))
        solution = (vertices, solution._2)
        true
      case Nil => false
    }
  }

  def setUpVertexStyles(): Unit = {
    val stylesheet = graphView.getStylesheet
    val notVisited = new util.HashMap[String, Object]()
    notVisited.put(STYLE_SHAPE, SHAPE_ELLIPSE)
    notVisited.put(STYLE_OPACITY, Integer.valueOf(50))
    notVisited.put(STYLE_FONTCOLOR, "#774400")
    notVisited.put(STYLE_FONTSTYLE, Integer.valueOf(FONT_BOLD))
    notVisited.put(STYLE_FONTSIZE, Integer.valueOf(14))
    stylesheet.putCellStyle("NOT_VISITED", notVisited)
    val visited = new util.HashMap[String, Object](notVisited)
    visited.put(STYLE_FILLCOLOR, "#FF0000")
    stylesheet.putCellStyle("VISITED", visited)
  }
}