package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.Model.Node.NodeStatus;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.ExecutionMonitor;
import com.example.treevisualize.View.Visualizer.TreeObserver;

import java.util.ArrayList;
import java.util.List;


public abstract class Tree {
    protected Node root;
    private List<TreeObserver> observers;
    protected ExecutionMonitor monitor;
    public Tree() {
        this.root = null;
        this.observers = new ArrayList<>();
    }
    public List<Node> traverse(TraversalStrategy strategy) {
        if (this.root == null) {
            return new ArrayList<>();
        }
        List<Node> path = strategy.traverse(this,this.root);
        for (Node node : path) {
            visit(node);
        }
        return path;
    }

    protected void visit(Node node) {
        if (node == null) return;
        node.changeStatus(NodeStatus.ACTIVE);
        notifyNodeChanged(node);
        node.changeStatus(NodeStatus.NORMAL);
        notifyNodeChanged(node);
    }

    public void resetTreeStatus() {
        if (root != null) {
            resetRecursive(root);
            notifyStructureChanged();
        }
    }

    public void addObserver(TreeObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(TreeObserver observer) {
        observers.remove(observer);
    }
    public abstract void insert(int value);

    public abstract void delete(int value);

    public abstract Node search(int value);

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node newRoot) {
        this.root = newRoot;
        notifyStructureChanged();
    }

    public void clear() {
        this.root = null;
        notifyStructureChanged();
    }

    protected void notifyStructureChanged() {
        for (TreeObserver observer : observers) {
            observer.onStructureChanged();
        }
    }

    protected void notifyNodeChanged(Node node) {
        for (TreeObserver observer : observers) {
            observer.onNodeChanged(node);
        }
    }

    protected void notifyError(String message) {
        for (TreeObserver observer : observers) {
            observer.onError(message);
        }
    }

    private void resetRecursive(Node node) {
        if (node == null) return;
        node.changeStatus(NodeStatus.NORMAL);
        for (Node child : node.getChildren()) {
            resetRecursive(child);
        }
    }

    //statistic: number of nodes, height

    public int getNodeCount() {
        return countNodesRecursive(root);
    }

    private int countNodesRecursive(Node node) {
        if (node == null) return 0;

        int count = 1;
        for (Node child : node.getChildren()) {
            count += countNodesRecursive(child);
        }
        return count;
    }
    public int getHeight() {
        return calculateHeightRecursive(root);
    }

    private int calculateHeightRecursive(Node node) {
        if (node == null) return 0;

        int maxChildHeight = 0;

        for (Node child : node.getChildren()) {
            int h = calculateHeightRecursive(child);
            if (h > maxChildHeight) maxChildHeight = h;
        }

        return 1 + maxChildHeight;
    }
    public void setMonitor(ExecutionMonitor monitor) {
        this.monitor = monitor;
    }

    // [MỚI] Hàm báo cáo ngữ nghĩa (Semantic Notification)
    // Các class con (BST, AVL...) sẽ gọi hàm này thay vì updatePseudoStep(int)
    protected void notifyEvent(AlgorithmEvent event, Node node) {
        if (monitor != null) {
            monitor.onEvent(event, node);
        }
    }

    // Hàm cập nhật dòng code (được gọi ngược lại từ Executor sau khi mapping)
    public void updatePseudoStep(int lineIndex) {
        for (TreeObserver obs : observers) {
            obs.onPseudoStep(lineIndex); // Báo cho Recorder chụp ảnh
        }
    }
}
