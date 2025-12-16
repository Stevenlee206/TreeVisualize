package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.NodeStatus;
import com.example.treevisualize.Visualizer.TreeObserver;

import java.util.ArrayList;
import java.util.List;


public abstract class Tree {
    protected Node root;
    private List<TreeObserver> observers;
    public Tree() {
        this.root = null;
        this.observers = new ArrayList<>();
    }
    public List<Node> traverse(TraversalStrategy strategy) {
        if (this.root == null) {
            return new ArrayList<>();
        }
        List<Node> path = strategy.traverse(this.root);
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

    public void resetTreeStatus() {
        if (root != null) {
            resetRecursive(root);
            notifyStructureChanged();
        }
    }

    private void resetRecursive(Node node) {
        if (node == null) return;
        node.changeStatus(NodeStatus.NORMAL);

        if (node instanceof BinaryTreeNode) {
            var bNode = (BinaryTreeNode) node;
            resetRecursive(bNode.getLeftChild());
            resetRecursive(bNode.getRightChild());
        }
        else if (node instanceof GeneralTreeNode) {
            var gNode = (GeneralTreeNode) node;
            resetRecursive(gNode.getLeftMostChild());
            resetRecursive(gNode.getRightSibling());
        }
    }
    
    //statistic: number of nodes, height
    
    public abstract int getNodeCount() ;
    public abstract int getHeight();

}