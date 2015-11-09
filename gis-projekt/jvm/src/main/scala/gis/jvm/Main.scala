package gis.jvm

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Dimension, GridBagLayout, Insets}
import javax.swing._

import gis.shared.algorithms.LazyDFSBasedGraphConnectivity

object Main extends JFrame with Graphs {
  var graphVisualization = new GraphVisualization(this)

  def main(args: Array[String]): Unit = {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    this.setSize(1200, 800)
    this.setFocusable(false)
    this.setLayout(new GridBagLayout())
    graphVisualization.visualize(new LazyDFSBasedGraphConnectivity(connectedGraph).solve, connectedGraph)
    this.getContentPane.add(panel)
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
    val comboBox = new JComboBox[String]()
    comboBox.addActionListener(new ActionListener {
      override def actionPerformed(e: ActionEvent): Unit = {
        comboBox.getSelectedItem match {
          case "2 connected" =>
            jFrame.getContentPane.removeAll()
            graphVisualization = new GraphVisualization(jFrame)
            graphVisualization.visualize(new LazyDFSBasedGraphConnectivity(secondDisconnectedGraph).solve, secondDisconnectedGraph)
            jFrame.getContentPane.add(panel)
            SwingUtilities.updateComponentTreeUI(jFrame)
          case _ =>
        }
      }
    })
    comboBox
  }
}
