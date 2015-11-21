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
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static gis.jvm.View.GraphType.CONNECTED;
import static gis.jvm.View.GraphType.DISCONNECTED;

public class View extends JFrame {
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;
    private JPanel mainPanel;
    private JButton nextStepButton;
    private JComboBox<String> predefinedComboBox;
    private JPanel graphPanel;
    private JButton generateDisconnectedButton;
    private JButton generateConnectedButton;
    private JTextField verticesNumTextField;
    private JProgressBar algorithmProgressBar;

    GraphVisualization graphVisualization = new GraphVisualization(graphPanel);

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel1, gbc);
        graphPanel = new JPanel();
        graphPanel.setLayout(new GridBagLayout());
        panel1.add(graphPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(261, 24), null, 0, true));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(10, 1, new Insets(30, 30, 30, 30), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(90, 396), null, 0, false));
        predefinedComboBox = new JComboBox();
        panel2.add(predefinedComboBox, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nextStepButton = new JButton();
        nextStepButton.setText("Next step");
        nextStepButton.setMnemonic('N');
        nextStepButton.setDisplayedMnemonicIndex(0);
        panel2.add(nextStepButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateDisconnectedButton = new JButton();
        generateDisconnectedButton.setText("Generate Disconnected");
        panel2.add(generateDisconnectedButton, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setFont(UIManager.getFont("InternalFrame.titleFont"));
        label1.setText("Predefined graphs");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setFont(UIManager.getFont("InternalFrame.titleFont"));
        label2.setText("Generation");
        panel2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setFont(UIManager.getFont("InternalFrame.titleFont"));
        label3.setText("Algorithm");
        panel2.add(label3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        generateConnectedButton = new JButton();
        generateConnectedButton.setText("Generate Connected");
        panel2.add(generateConnectedButton, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        verticesNumTextField = new JTextField();
        verticesNumTextField.setText("");
        verticesNumTextField.setToolTipText("Enter vertices number");
        panel2.add(verticesNumTextField, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTHWEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Vertices number");
        panel2.add(label4, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_SOUTH, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        algorithmProgressBar = new JProgressBar();
        panel2.add(algorithmProgressBar, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    public enum GraphType {
        DISCONNECTED,
        CONNECTED
    }

    public View() {
        super("Graph Visualization");
        setUp();
    }

    private void setUp() {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setUpPredefinedComboBox(predefinedComboBox);
        this.setVisible(true);

        nextStepButton.addActionListener((e) -> {
            algorithmProgressBar.setValue(algorithmProgressBar.getValue() + 1);
            graphVisualization.nextStep();
        });
        generateDisconnectedButton.addActionListener(e -> generateGraph(getVerticesNum(), DISCONNECTED));
        generateConnectedButton.addActionListener(e -> generateGraph(getVerticesNum(), CONNECTED));
    }

    private int getVerticesNum() {
        return Integer.parseInt(verticesNumTextField.getText());
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
        comboBox.addActionListener(e -> visualizeGraph(comboBox, graphMap));
    }

    private void visualizeGraph(JComboBox<String> comboBox, Map<String, Graph> graphMap) {
        graphVisualization = new GraphVisualization(graphPanel);
        String selectedItem = (String) comboBox.getSelectedItem();
        Graph selectedGraph = graphMap.get(selectedItem);
        Tuple2<List<Object>, Object> solution = new MultipleDFSBasedAlgorithm(selectedGraph).solve();
        algorithmProgressBar.setValue(0);
        algorithmProgressBar.setMaximum(solution._1().size());
        graphVisualization.visualize(solution, selectedGraph);
        SwingUtilities.updateComponentTreeUI(this);
    }
}
