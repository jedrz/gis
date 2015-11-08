package gis.jvm

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Dimension, GridBagLayout}
import javax.swing.{JButton, JComboBox, JFrame, JPanel}

import gis.shared.algorithms.LazyDFSBasedGraphConnectivity

object Main extends JFrame with Graphs {

  def main(args: Array[String]): Unit = {
    val jFrame = this
    var graphVisualization = new GraphVisualization(this)
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    this.setSize(1200, 800)
    this.setFocusable(false)
    this.setLayout(new GridBagLayout())
    graphVisualization.visualize(new LazyDFSBasedGraphConnectivity(connectedGraph).solve, connectedGraph)
    val button = new JButton("Next step")
    button.setMaximumSize(new Dimension(200, 100))
    button.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = graphVisualization.nextStep
    })

    val comboBox = new JComboBox[String]()
    comboBox.addItem("1 connected")
    comboBox.addItem("2 connected")
    comboBox.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        comboBox.getSelectedItem match {
          case "2 connected" =>
            graphVisualization = new GraphVisualization(jFrame)
            graphVisualization.visualize(new LazyDFSBasedGraphConnectivity(connectedGraph).solve, secondDisconnectedGraph)
          case _ =>
        }
      }
    })
    val panel = new JPanel()
    panel.setLayout(new GridBagLayout)
    panel.add(button)
    panel.add(comboBox)
    this.getContentPane.add(panel)
    this.setVisible(true)
  }
}
