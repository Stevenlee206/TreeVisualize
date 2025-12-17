package com.example.treevisualize.Node;

public class ScapegoatTreeNode extends BinaryTreeNode {
    public ScapegoatTreeNode(int value) {
        super(value);
    }

    @Override
    public Node copy() {
        ScapegoatTreeNode newNode = new ScapegoatTreeNode(this.getValue());
        newNode.changeStatus(this.getStatus());
        if (getLeftChild() != null) newNode.setLeftChild((BinaryTreeNode) getLeftChild().copy());
        if (getRightChild() != null) newNode.setRightChild((BinaryTreeNode) getRightChild().copy());
        return newNode;
    }
}