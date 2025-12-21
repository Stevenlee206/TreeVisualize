package com.example.treevisualize.Model.Node;

import java.util.ArrayList;
import java.util.List;

public class GeneralTreeNode extends Node {
    private GeneralTreeNode leftMostChild;
    private GeneralTreeNode rightSibling;

    public void setLeftMostChild(GeneralTreeNode leftMostChild) {
        this.leftMostChild = leftMostChild;
    }

    public void setRightSibling(GeneralTreeNode rightSibling) {
        this.rightSibling = rightSibling;
    }

    public GeneralTreeNode(int value) {
        super(value);
        this.leftMostChild = null;
        this.rightSibling = null;
    }

    public GeneralTreeNode getRightSibling() {
        return rightSibling;
    }

    public GeneralTreeNode getLeftMostChild() {
        return leftMostChild;
    }

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
            GeneralTreeNode currentChild = childCopy;
            while (currentChild != null) {
                currentChild.setParent(newNode); // Gán cha cho từng node trong hàng
                currentChild = currentChild.getRightSibling();
            }
        }
        if (this.getRightSibling() != null) {
            GeneralTreeNode siblingCopy = (GeneralTreeNode) this.getRightSibling().copy();
            newNode.setRightSibling(siblingCopy);
        }

        return newNode;
    }

    @Override
    public List<Node> getChildren() {
        List<Node> children = new ArrayList<>();
        GeneralTreeNode current = this.leftMostChild;
        while (current != null) {
            children.add(current);
            current = current.getRightSibling();
        }

        return children;
    }}
