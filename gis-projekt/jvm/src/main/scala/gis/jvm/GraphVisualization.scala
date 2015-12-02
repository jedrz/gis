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
  var graphView: mxGraph = new mxGraph
  var vertexMap: Map[Int, mxCell] = Map.empty
  var solution: Solution = (Nil, false)
  var solutionScc: (SCC, Graph, Boolean) = (Stream.empty, new Graph(), false)
  var stepNo: Int = 0
  var colors = List.empty[String]

  def visualizeGraph(graph: Graph): Unit = {
    jPanel.removeAll()
    graphView = new mxGraph()
    addVertexStyle("default")
    graphView.getModel.beginUpdate()
    graph.vertices.foreach(addVertex)
    graph.edges.foreach(addEdge)
    graphView.getModel.endUpdate()
    graphView.setCellsSelectable(false)
    colors.foreach(addVertexStyle)
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
    val addedVertex = graphView.insertVertex(graphView.getDefaultParent, vertex.toString, vertex, 1, 1, 50, 50, "default")
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

  def visualize(solution: (SCC, Graph, Boolean), graph: Graph): Unit = {
    solutionScc = solution
    colors = rainbow(solutionScc._1.size)
    addVertexStyle("default")
    colors.foreach(addVertexStyle)
    visualizeGraph(graph)
    graphView.setAutoSizeCells(true)
    graphView.setCellsLocked(true)
  }

  def nextStep: Boolean = {
    if(solution._1.isEmpty) {
      nextStepScc
    }
    else {
      nextStepDefault
    }
  }

  def nextStepScc: Boolean = {
    solutionScc._1 match {
      case nextComponent #:: components =>
        nextComponent.foreach(colorVertex)
        stepNo += 1
        solutionScc = (components, solutionScc._2, solutionScc._3)
        true
      case _ =>
        false
    }
  }

  def nextStepDefault: Boolean = {
    solution._1 match {
      case nextVertex :: vertices =>
        colorVertex(nextVertex)
        solution = (vertices, solution._2)
        true
      case Nil => false
    }
  }

  def colorVertex(vertex: Vertex): Unit = {
    val visualizedVertex = vertexMap(vertex)
    visualizedVertex.setValue(visualizedVertex.getId + "/" + stepNo)
    graphView.setCellStyle(colors(stepNo), Array(visualizedVertex))
    graphView.refresh()
  }

  def colorVertex(vertex: Vertex, color : String): Unit = {
    val visualizedVertex = vertexMap(vertex)
    graphView.setCellStyle(color, Array(visualizedVertex))
    graphView.refresh()
  }

  def addVertexStyle(color : String): Unit = {
    val stylesheet = graphView.getStylesheet
    val style = new util.HashMap[String, Object]()
    style.put(STYLE_SHAPE, SHAPE_ELLIPSE)
    style.put(STYLE_OPACITY, Integer.valueOf(50))
    style.put(STYLE_FONTCOLOR, "#774400")
    if (color != "default") {
      style.put(STYLE_FILLCOLOR, color)
    }
    style.put(STYLE_FONTSTYLE, Integer.valueOf(FONT_BOLD))
    style.put(STYLE_FONTSIZE, Integer.valueOf(14))
    stylesheet.putCellStyle(color, style)
    graphView.setStylesheet(stylesheet)
  }

  def colorVertices(colors : List[String]): Unit ={
    colors.indices.foreach(i => colorVertex(i, colors(i)))
  }

  def rainbow(length : Int) : List[String] = {
    val center = 200
    val width = 55

    (0 to (length - 1)).foldLeft(List.empty[String])((list, i) => {
      val red = Math.sin(0.6 * i + 0) * width + center
      val green = Math.sin(0.6 * i + 2) * width + center
      val blue = Math.sin(0.6 * i + 4) * width + center
      val hexColor = "#" + toHexString(red) + toHexString(green) + toHexString(blue)
      hexColor :: list
    })
  }

  def toHexString(double: Double) : String = {
    Integer.toHexString(double.asInstanceOf[Int])
  }
}
