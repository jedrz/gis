package gis.jvm;

import gis.shared.Graph;
import gis.shared.Graphs;
import gis.shared.algorithms.other.LazyDFSBasedGraphConnectivity;
import scala.runtime.AbstractFunction1;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class View extends JFrame {
    private JPanel panel1;
    private JButton nextStepButton;
    private JComboBox<String> comboBox1;
    private JPanel graphPanel;

    GraphVisualization graphVisualization = new GraphVisualization(graphPanel);

    public View() {
        super("Graph Visualization");
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        setUpComboBox(comboBox1);
        this.setVisible(true);

        nextStepButton.addActionListener((e) -> graphVisualization.nextStep());
    }

    public void setUpComboBox(JComboBox<String> comboBox) {
        Map<String, Graph> graphMap = new HashMap<>();
        Graphs.partiallyConnectedGraphs().foreach(new AbstractFunction1<Graph, String>() {
            @Override
            public String apply(Graph g) {
                String itemName = "partially connected - " + comboBox.getItemCount();
                graphMap.put(itemName, g);
                comboBox.addItem(itemName);
                return itemName;
            }
        });
        Graphs.notPartiallyConnectedGraphs().foreach(new AbstractFunction1<Graph, String>() {
            @Override
            public String apply(Graph g) {
                String itemName = "not partially connected - " + comboBox.getItemCount();
                graphMap.put(itemName, g);
                comboBox.addItem(itemName);
                return itemName;
            }
        });
        comboBox.addActionListener(e -> {
            graphVisualization = new GraphVisualization(graphPanel);
            String selectedItem = (String) comboBox.getSelectedItem();
            Graph selectedGraph = graphMap.get(selectedItem);
            graphVisualization.visualize(new LazyDFSBasedGraphConnectivity(selectedGraph).solve(), selectedGraph);
            SwingUtilities.updateComponentTreeUI(this);
        });
    }
}
