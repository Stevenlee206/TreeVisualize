package com.example.treevisualize.Model.Node;

import java.util.ArrayList;
import java.util.List;

public class BinaryTreeNode extends Node{
    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;

    public BinaryTreeNode(int value) {
        super(value);
        this.leftChild = null;
        this.rightChild = null;
    }

    public BinaryTreeNode getLeftChild() {
        return leftChild;
    }

    public BinaryTreeNode getRightChild() {
        return rightChild;
    }

    public void setLeftChild(BinaryTreeNode child) {
        this.leftChild = child;
        if (child != null) {
            child.setParent(this);
        }
    }

    public void setRightChild(BinaryTreeNode child) {
        this.rightChild = child;
        if (child != null) {
            child.setParent(this);
        }
    }
    @Override
    public Node copy() {
        BinaryTreeNode newNode = new BinaryTreeNode(this.getValue());
        newNode.changeStatus(this.getStatus());
        if (getLeftChild() != null) {
            newNode.setLeftChild((BinaryTreeNode) getLeftChild().copy());
        }
        if (getRightChild() != null) {
            newNode.setRightChild((BinaryTreeNode) getRightChild().copy());
        }
        return newNode;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();
        if (leftChild != null) {
            children.add(leftChild);
        }
        if (rightChild != null) {
            children.add(rightChild);
        }
        return children;
    }
}
