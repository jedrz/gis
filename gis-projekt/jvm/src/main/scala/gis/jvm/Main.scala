package gis.jvm

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Dimension, GridBagLayout, Insets}
import javax.swing._

import gis.shared.algorithms.LazyDFSBasedGraphConnectivity
import gis.shared.{Graph, Graphs}

object Main extends JFrame with Graphs {
  var graphVisualization = new GraphVisualization(this)
  val propertiesPanel = panel

  def main(args: Array[String]): Unit = {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    this.setSize(1200, 800)
    this.setFocusable(false)
    this.setLayout(new GridBagLayout())
    graphVisualization.visualize(new LazyDFSBasedGraphConnectivity(connectedGraph).solve, connectedGraph)
    this.getContentPane.add(propertiesPanel)
    this.setVisible(true)
  }

  def panel: JPanel = {
    val panel = new JPanel()
    panel.setLayout(new GridBagLayout)
    panel.add(button)
    panel.add(comboBox)
    panel
  }

  def button: JButton = {
    val button = new JButton("Next step")
    button.setMaximumSize(new Dimension(200, 100))
    button.setMargin(new Insets(0, 0, 0, 20))
    button.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = graphVisualization.nextStep
    })
    button
  }


  def comboBox: JComboBox[String] = {
    val jFrame = this
    var graphMap = Map.empty[String, Graph]

    def addConnectedGraph(comboBox: JComboBox[String], graph: Graph): JComboBox[String] = {
      val itemName = "connected - " + comboBox.getItemCount
      graphMap = graphMap + (itemName -> graph)
      comboBox.addItem(itemName)
      comboBox
    }

    def addDisconnectedGraph(comboBox: JComboBox[String], graph: Graph): JComboBox[String] = {
      val itemName = "disconnected - " + comboBox.getItemCount
      graphMap = graphMap + (itemName -> graph)
      comboBox.addItem(itemName)
      comboBox
    }

    var fullComboBox = connectedGraphs.foldLeft(new JComboBox[String])((box, graph) => {
      addConnectedGraph(box, graph)
    })
    fullComboBox = disconnectedGraphs.foldLeft(fullComboBox)((box, graph) => {
      addDisconnectedGraph(box, graph)
    })
    fullComboBox.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        fullComboBox.getSelectedItem match {
          case name: String =>
            jFrame.getContentPane.removeAll()
            graphVisualization = new GraphVisualization(jFrame)
            graphVisualization.visualize(new LazyDFSBasedGraphConnectivity(graphMap(name)).solve, graphMap(name))
            jFrame.getContentPane.add(propertiesPanel)
            SwingUtilities.updateComponentTreeUI(jFrame)
          case _ =>
        }
      }
    })
    fullComboBox
  }
}
