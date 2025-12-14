package com.example.treevisualize.Node;

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

    /**
     * Phương thức abstract bắt buộc các con phải tự định nghĩa.
     * Trả về một bản sao sâu (Deep Copy) của chính nó.
     */
    public abstract Node copy();
}
