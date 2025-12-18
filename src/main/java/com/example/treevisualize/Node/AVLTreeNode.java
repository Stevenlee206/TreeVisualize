package com.example.treevisualize.Node;

public class AVLTreeNode extends BinaryTreeNode {
    private int height;

    public AVLTreeNode(int value) {
        super(value);
        this.height = 1; // Node mới thêm vào luôn có height là 1
    }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    @Override
    public Node copy() {
        AVLTreeNode newNode = new AVLTreeNode(this.getValue());
        newNode.setHeight(this.height);
        newNode.changeStatus(this.getStatus());
        if (getLeftChild() != null) newNode.setLeftChild((BinaryTreeNode) getLeftChild().copy());
        if (getRightChild() != null) newNode.setRightChild((BinaryTreeNode) getRightChild().copy());
        return newNode;
    }
}