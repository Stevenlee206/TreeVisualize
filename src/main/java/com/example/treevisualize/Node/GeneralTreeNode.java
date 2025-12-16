package com.example.treevisualize.Node;

public class GeneralTreeNode extends Node {
    private GeneralTreeNode leftMostChild;
    private GeneralTreeNode rightSibling;
    // update from requirements , check later
    public void setLeftMostChild(GeneralTreeNode leftMostChild) {
        this.leftMostChild = leftMostChild;
    }

    public void setRightSibling(GeneralTreeNode rightSibling) {
        this.rightSibling = rightSibling;
    }
    // constructor
    public GeneralTreeNode(int value) {
        super(value);
        this.leftMostChild = null;
        this.rightSibling = null;
    }
    // getter
    public GeneralTreeNode getRightSibling() {
        return rightSibling;
    }

    public GeneralTreeNode getLeftMostChild() {
        return leftMostChild;
    }
    // add a child to a node
    public void addChild(GeneralTreeNode child) {


        if (child==null){ return; }
        child.setParent(this);

        if (leftMostChild == null) {
            leftMostChild = child;
            return;
        }
        GeneralTreeNode current=leftMostChild;
        while (current.getRightSibling() != null) {
            current = current.getRightSibling();
        }
        current.rightSibling = child;
    }
    // remove a child
    public void removeChild(GeneralTreeNode child) {


        if (this.leftMostChild == null || child == null) return;

        if (leftMostChild.getValue() == child.getValue()) {
            GeneralTreeNode nodeToRemove = leftMostChild;
            this.leftMostChild = this.leftMostChild.getRightSibling();
            nodeToRemove.setParent(null);
            nodeToRemove.setRightSibling(null);
            return;
        }
        GeneralTreeNode current = this.leftMostChild;
        while (current.getRightSibling() != null) {
            if (current.getRightSibling().getValue() == child.getValue()) {
                GeneralTreeNode nodeToRemove = current.getRightSibling();
                current.setRightSibling(nodeToRemove.getRightSibling());
                nodeToRemove.setParent(null);
                nodeToRemove.setRightSibling(null);
                return;
            }
            current = current.getRightSibling();
        }
    }


    @Override
    public Node copy() {
        GeneralTreeNode newNode = new GeneralTreeNode(this.getValue());

        newNode.changeStatus(this.getStatus());
        // -----------------------------------

        if (this.getLeftMostChild() != null) {
            GeneralTreeNode childCopy = (GeneralTreeNode) this.getLeftMostChild().copy();
            newNode.setLeftMostChild(childCopy);
            childCopy.setParent(newNode);
        }
        if (this.getRightSibling() != null) {
            GeneralTreeNode siblingCopy = (GeneralTreeNode) this.getRightSibling().copy();
            newNode.setRightSibling(siblingCopy);
        }

        return newNode;
    }
}
