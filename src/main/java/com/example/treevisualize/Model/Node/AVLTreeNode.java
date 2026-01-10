package com.example.treevisualize.Model.Node;

public class AVLTreeNode extends BinaryTreeNode {
    private int height;

    public AVLTreeNode(int value) {
        super(value);
        this.height = 1;
    }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    @Override
    public Node copy() {
        AVLTreeNode newNode = new AVLTreeNode(this.getValue());
        newNode.setHeight(this.height);
        newNode.changeStatus(this.getStatus());
        if (this.getLeftChild() != null) {
            newNode.setLeftChild((BinaryTreeNode) this.getLeftChild().copy());
        }

        if (this.getRightChild() != null) {
            newNode.setRightChild((BinaryTreeNode) this.getRightChild().copy());
        }

        return newNode;
    }
public int calculateBalanceFactor() {
    int leftH = (this.getLeftChild() instanceof AVLTreeNode) ? ((AVLTreeNode) this.getLeftChild()).getHeight() : 0;
    int rightH = (this.getRightChild() instanceof AVLTreeNode) ? ((AVLTreeNode) this.getRightChild()).getHeight() : 0;
    return leftH - rightH;
}
}
