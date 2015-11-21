package gis.jvm

import java.awt.GridBagConstraints
import java.util
import javax.swing.{BorderFactory, JPanel}

import com.mxgraph.layout.mxFastOrganicLayout
import com.mxgraph.model.mxCell
import com.mxgraph.swing.mxGraphComponent
import com.mxgraph.util.mxConstants._
import com.mxgraph.view.mxGraph
import gis.shared._

class GraphVisualization(newJPanel: JPanel) {
  val jPanel: JPanel = newJPanel
  val graphView: mxGraph = new mxGraph
  var vertexMap: Map[Int, mxCell] = Map.empty
  var solution: Solution = (Nil, false)
  var stepNo: Int = 1

  def visualizeGraph(graph: Graph): Unit = {
    jPanel.removeAll()
    setUpVertexStyles()
    graphView.getModel.beginUpdate()
    graph.vertices.foreach(addVertex)
    graph.edges.foreach(addEdge)
    graphView.getModel.endUpdate()
    graphView.setCellsSelectable(false)
    val graphComponent = new mxGraphComponent(graphView)
    graphComponent.setBorder(BorderFactory.createEmptyBorder())
    graphComponent.setFocusable(false)
    graphComponent.setConnectable(false)
    graphComponent.getLayout
    val myLayout = new mxFastOrganicLayout(graphView)
    myLayout.setUseBoundingBox(false)
    myLayout.execute(graphView.getDefaultParent)
    jPanel.add(graphComponent, new GridBagConstraints)
  }

  def addVertex(vertex: Vertex): Unit = {
    val addedVertex = graphView.insertVertex(graphView.getDefaultParent, vertex.toString, vertex, 1, 1, 50, 50, "NOT_VISITED")
    vertexMap += (vertex -> addedVertex.asInstanceOf[mxCell])
  }

  def addEdge(edge : Edge): Unit = {
    val id = edge._1 + "-" + edge._2
    val source = vertexMap.get(edge._1).orNull
    val target = vertexMap.get(edge._2).orNull
    graphView.insertEdge(graphView.getDefaultParent, id, "", source, target)
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
