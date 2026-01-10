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
    private final List<TreeObserver> observers;
    protected ExecutionMonitor monitor;

    public Tree() {
        this.root = null;
        this.observers = new ArrayList<>();
    }

    public abstract void insert(int value);//create root if tree is empty
    public abstract void insert(int parentValue, int childValue);//expand tree by adding child to parent

    public abstract void delete(int value);
    public abstract Node search(int value);

    
    public void setRoot(Node newRoot) {
        this.root = newRoot;
        notifyStructureChanged();
    }

    // ===== COMMON TRAVERSAL & VISITOR =====
    public List<Node> traverse(TraversalStrategy strategy) {
        if (this.root == null) return new ArrayList<>();
        List<Node> path = strategy.traverse(this, this.root);
        for (Node node : path) visit(node);
        return path;
    }

    protected void visit(Node node) {
        if (node == null) return;
        node.changeStatus(NodeStatus.ACTIVE);
        notifyNodeChanged(node);
        node.changeStatus(NodeStatus.NORMAL);
        notifyNodeChanged(node);
    }

    // ===== OBSERVER / MONITOR HELPERS =====
    public void setMonitor(ExecutionMonitor monitor) { this.monitor = monitor; }
    
    public void notifyEvent(AlgorithmEvent event, Node node) {
        if (monitor != null) monitor.onEvent(event, node);
    }
    
    public void notifyStructureChanged() { observers.forEach(TreeObserver::onStructureChanged); }
    public void notifyNodeChanged(Node node) { observers.forEach(o -> o.onNodeChanged(node)); }
    public void notifyError(String message) { observers.forEach(o -> o.onError(message)); }

    // ===== GETTERS / SETTERS =====
    public Node getRoot() { return root; }
    
    public void clear() {
        this.root = null;
        notifyStructureChanged();
    }
    
    public void addObserver(TreeObserver observer) { if (!observers.contains(observer)) observers.add(observer); }
    public void removeObserver(TreeObserver observer) { observers.remove(observer); }


    // ===== STATISTICS =====
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
        int max = 0;
        for (Node child : node.getChildren()) {
            max = Math.max(max, calculateHeightRecursive(child));
        }
        return 1 + max;
    }
    public void resetTreeStatus() {
        if (root != null) {
            resetRecursive(root);
            notifyStructureChanged();
        }
    }
    private void resetRecursive(Node node) {
        if (node == null) return;
        node.changeStatus(NodeStatus.NORMAL);
        for (Node child : node.getChildren()) resetRecursive(child);

    }
    

    // Hàm cập nhật dòng code (được gọi ngược lại từ Executor sau khi mapping)
    public void updatePseudoStep(int lineIndex) {
        for (TreeObserver obs : observers) {
            obs.onPseudoStep(lineIndex); // Báo cho Recorder chụp ảnh
        }
    }

}