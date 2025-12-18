package com.example.treevisualize.Node;

public class ScapegoatTreeNode extends BinaryTreeNode {
    private int size;

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public ScapegoatTreeNode(int value) {
        super(value);
        this.size=1;
    }

    @Override
    public Node copy() {
        ScapegoatTreeNode newNode = new ScapegoatTreeNode(this.getValue());
        newNode.changeStatus(this.getStatus());
        newNode.setSize(this.size);
        if (getLeftChild() != null) newNode.setLeftChild((BinaryTreeNode) getLeftChild().copy());
        if (getRightChild() != null) newNode.setRightChild((BinaryTreeNode) getRightChild().copy());
        return newNode;
    }

    public void updateSize() {
        int leftSize = (getLeftChild() != null) ? ((ScapegoatTreeNode) getLeftChild()).getSize() : 0;
        int rightSize = (getRightChild() != null) ? ((ScapegoatTreeNode) getRightChild()).getSize() : 0;
        this.size = 1 + leftSize + rightSize;
    }
}