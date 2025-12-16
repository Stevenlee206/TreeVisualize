package com.example.treevisualize.Visualizer;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.NodeColor;
import com.example.treevisualize.Node.RedBlackTreeNode;
import com.example.treevisualize.Trees.Tree;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
public class TreeVisualizer implements TreeObserver {

    private Canvas canvas;
    private GraphicsContext gc;
    private Tree tree;
    private Map<Node, NodeVisualizer> nodeVis;
    public static final double NODE_RADIUS = 20.0;
    public static final double VERTICAL_GAP = 60.0;

    public TreeVisualizer(Tree tree, Canvas canvas) {
        this.tree = tree;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.nodeVis = new HashMap<>();

        this.tree.addObserver(this);

        render();
    }

    @Override
    public void onNodeChanged(Node node) {
        render();
    }

    @Override
    public void onStructureChanged() {
        nodeVis.clear();
        render();
    }

    @Override
    public void onError(String message) {
        System.err.println("TreeVisualizer Error: " + message);
    }
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (tree == null || tree.getRoot() == null) {
            return;
        }
        double startX = canvas.getWidth() / 2;
        double startY = NODE_RADIUS * 2.5;
        double initialHGap = canvas.getWidth() / 4;

        calculateLayout(tree.getRoot(), startX, startY, initialHGap);
    }

    private void calculateLayout(Node node, double x, double y, double hGap) {
        if (node == null) return;

        syncNodeVisualizer(node);
        NodeVisualizer vis = nodeVis.get(node);

        vis.updatePosition(x, y);
        vis.setValue(node.getValue());

        updateNodeColor(vis, node);

        Node left = null;
        Node right = null;

        if (node instanceof BinaryTreeNode) {
            left = ((BinaryTreeNode) node).getLeftChild();
            right = ((BinaryTreeNode) node).getRightChild();
        } else if (node instanceof GeneralTreeNode) {
            left = ((GeneralTreeNode) node).getLeftMostChild();
            right = ((GeneralTreeNode) node).getRightSibling();
        }

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);

        if (left != null) {
            gc.strokeLine(x, y, x - hGap, y + VERTICAL_GAP);
            calculateLayout(left, x - hGap, y + VERTICAL_GAP, hGap / 2);
        }

        if (right != null) {
            gc.strokeLine(x, y, x + hGap, y + VERTICAL_GAP);
            calculateLayout(right, x + hGap, y + VERTICAL_GAP, hGap / 2);
        }

        vis.draw(gc);
    }

    private void syncNodeVisualizer(Node node) {
        if (!nodeVis.containsKey(node)) {
            NodeVisualizer vis = new NodeVisualizer();
            vis.setRadius(NODE_RADIUS);
            nodeVis.put(node, vis);
        }
    }

    private void updateNodeColor(NodeVisualizer vis, Node node) {
        if (node.getStatus() != null && node.getStatus() != com.example.treevisualize.Node.NodeStatus.NORMAL) {
            switch (node.getStatus()) {
                case ACTIVE:
                    vis.setFillColor(Color.ORANGE);
                    return;
                case VISITED:
                    vis.setFillColor(Color.LIGHTBLUE); 
                    return;
                case FOUND:
                    vis.setFillColor(Color.LIMEGREEN);
                    return;
                case DELETED:
                    vis.setFillColor(Color.GRAY);
                    return;
            }
        }

        if (node instanceof RedBlackTreeNode) {
            RedBlackTreeNode rbNode = (RedBlackTreeNode) node;
            if (rbNode.getColor() == NodeColor.RED) {
                vis.setFillColor(Color.RED);
            } else {
                vis.setFillColor(Color.BLACK);
            }
        } else {
            vis.setFillColor(Color.BLUE);
        }
    }
    public Canvas getCanvas() {
        return this.canvas;
    }
}
