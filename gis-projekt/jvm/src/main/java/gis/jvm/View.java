package gis.jvm;

import gis.shared.Generators;
import gis.shared.Graph;
import gis.shared.Graphs;
import gis.shared.algorithms.MultipleDFSBasedAlgorithm;
import org.scalacheck.Gen;
import scala.Tuple2;
import scala.collection.immutable.List;
import scala.runtime.AbstractFunction1;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static gis.jvm.View.GraphType.CONNECTED;
import static gis.jvm.View.GraphType.DISCONNECTED;

public class View extends JFrame {
    private JPanel mainPanel;
    private JButton nextStepButton;
    private JComboBox<String> predefinedComboBox;
    private JPanel graphPanel;
    private JButton generateDisconnectedButton;
    private JButton generateConnectedButton;
    private JTextField verticesNumTextField;
    private JProgressBar algorithmProgressBar;

    GraphVisualization graphVisualization = new GraphVisualization(graphPanel);

    public View() {
        super("Graph Visualization");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        setUpPredefinedComboBox(predefinedComboBox);
        this.setVisible(true);

        nextStepButton.addActionListener((e) -> {
            algorithmProgressBar.setValue(algorithmProgressBar.getValue() + 1);
            graphVisualization.nextStep();
        });
        generateDisconnectedButton.addActionListener(e -> generateGraph(Integer.parseInt(verticesNumTextField.getText()), DISCONNECTED));
        generateConnectedButton.addActionListener(e -> generateGraph(Integer.parseInt(verticesNumTextField.getText()), CONNECTED));
    }

    private void generateGraph(int verticesNum, GraphType graphType) {
        Graph graph = getGeneratedGraph(verticesNum, graphType);
        graphVisualization = new GraphVisualization(graphPanel);
        Tuple2<List<Object>, Object> solution = new MultipleDFSBasedAlgorithm(graph).solve();
        algorithmProgressBar.setValue(0);
        algorithmProgressBar.setMaximum(solution._1().size());
        graphVisualization.visualize(solution, graph);
        SwingUtilities.updateComponentTreeUI(this);
    }

    private Graph getGeneratedGraph(final int verticesNum, GraphType graphType) {
        Gen<Graph> graphGen = graphType == DISCONNECTED ? Generators.notPartiallyConnected() : Generators.partiallyConnected();
        return graphGen.suchThat(new AbstractFunction1<Graph, Object>() {
            @Override
            public Object apply(Graph v1) {
                return v1.vertices().size() < verticesNum;
            }
        }).sample().get();
    }


    private void setUpPredefinedComboBox(JComboBox<String> comboBox) {
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
            Tuple2<List<Object>, Object> solution = new MultipleDFSBasedAlgorithm(selectedGraph).solve();
            algorithmProgressBar.setValue(0);
            algorithmProgressBar.setMaximum(solution._1().size());
            graphVisualization.visualize(solution, selectedGraph);
            SwingUtilities.updateComponentTreeUI(this);
        });
    }

    public enum GraphType {
        DISCONNECTED,
        CONNECTED
    }
}
