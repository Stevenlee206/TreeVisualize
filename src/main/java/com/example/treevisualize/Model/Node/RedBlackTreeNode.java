package com.example.treevisualize.Model.Node;

public class RedBlackTreeNode extends BinaryTreeNode {
    private NodeColor  color;

    public RedBlackTreeNode(int value) {
        super(value);
        this.color = NodeColor.RED;
    }

    public void changeColor(NodeColor color) {
        this.color = color;
    }

    public NodeColor getColor() {
        return color;
    }

    @Override
    public Node copy() {

        RedBlackTreeNode newNode = new RedBlackTreeNode(this.getValue());


        newNode.changeColor(this.getColor());


        newNode.changeStatus(this.getStatus());



        if (this.getLeftChild() != null) {
            newNode.setLeftChild((BinaryTreeNode) this.getLeftChild().copy());
        }
        if (this.getRightChild() != null) {
            newNode.setRightChild((BinaryTreeNode) this.getRightChild().copy());
        }

        return newNode;
    }

    public boolean isRed() {
        return this.getColor() == NodeColor.RED;
    }
}