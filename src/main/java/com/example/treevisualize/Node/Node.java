package com.example.treevisualize.Node;

import java.util.List;

public abstract class Node {
    private Node parent;
    private int value;
    private NodeStatus status;

    public Node(int value) {
        this.value = value;
        this.parent=null;
    }
    public int getValue() {
        return value;
    }

    public Node getParent() {
        return parent;
    }

    public NodeStatus getStatus() {
        return status;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void changeValue(int value) {
        this.value = value;
    }

    public void changeStatus(NodeStatus status) {
        this.status = status;
    }

    public abstract Node copy();

    public abstract List<Node> getChildren();
}
