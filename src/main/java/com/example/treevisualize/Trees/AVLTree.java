package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.AVLTreeNode;
import com.example.treevisualize.Node.BinaryTreeNode;

public class AVLTree extends BinarySearchTree {

    private int height(BinaryTreeNode n) {
        if (n == null) return 0;
        if (n instanceof AVLTreeNode) {
            return ((AVLTreeNode) n).getHeight();
        }
        return 0;
    }
    private int getBalance(BinaryTreeNode n) {
        if (n == null) return 0;
        return height(n.getLeftChild()) - height(n.getRightChild());
    }

    @Override
    public void insert(int value) {
        root = insertRecursive((AVLTreeNode) this.root, value);
        notifyStructureChanged();
    }

    private AVLTreeNode insertRecursive(AVLTreeNode node, int value) {
        if (node == null) return new AVLTreeNode(value);

        if (value < node.getValue())
            node.setLeftChild(insertRecursive((AVLTreeNode) node.getLeftChild(), value));
        else if (value > node.getValue())
            node.setRightChild(insertRecursive((AVLTreeNode) node.getRightChild(), value));
        else return node; // Không nhận giá trị trùng

        // 1. Cập nhật height
        node.setHeight(1 + Math.max(height(node.getLeftChild()), height(node.getRightChild())));

        // 2. Kiểm tra cân bằng và xoay
        return rebalance(node);
    }

    private AVLTreeNode rebalance(AVLTreeNode node) {
        int balance = getBalance(node);

        // Trường hợp Left Left
        if (balance > 1 && getBalance(node.getLeftChild()) >= 0)
            return rightRotate(node);

        // Trường hợp Right Right
        if (balance < -1 && getBalance(node.getRightChild()) <= 0)
            return leftRotate(node);

        // Trường hợp Left Right
        if (balance > 1 && getBalance(node.getLeftChild()) < 0) {
            node.setLeftChild(leftRotate((AVLTreeNode) node.getLeftChild()));
            return rightRotate(node);
        }

        // Trường hợp Right Left
        if (balance < -1 && getBalance(node.getRightChild()) > 0) {
            node.setRightChild(rightRotate((AVLTreeNode) node.getRightChild()));
            return leftRotate(node);
        }

        return node;
    }

    private AVLTreeNode rightRotate(AVLTreeNode y) {
        AVLTreeNode x = (AVLTreeNode) y.getLeftChild();
        AVLTreeNode T2 = (AVLTreeNode) x.getRightChild();

        x.setRightChild(y);
        y.setLeftChild(T2);

        y.setHeight(Math.max(height(y.getLeftChild()), height(y.getRightChild())) + 1);
        x.setHeight(Math.max(height(x.getLeftChild()), height(x.getRightChild())) + 1);

        if (T2 != null) T2.setParent(y);

        x.setParent(y.getParent());
        y.setParent(x);

        return x;
    }

    private AVLTreeNode leftRotate(AVLTreeNode x) {
        AVLTreeNode y = (AVLTreeNode) x.getRightChild();
        AVLTreeNode T2 = (AVLTreeNode) y.getLeftChild();

        y.setLeftChild(x);
        x.setRightChild(T2);

        x.setHeight(Math.max(height(x.getLeftChild()), height(x.getRightChild())) + 1);
        y.setHeight(Math.max(height(y.getLeftChild()), height(y.getRightChild())) + 1);

        if (T2 != null) T2.setParent(x);

        y.setParent(x.getParent());
        x.setParent(y);

        return y;
    }

    @Override
    public void delete(int value) {
        this.root = deleteRecursive((AVLTreeNode) this.root, value);
        notifyStructureChanged();
    }

    private AVLTreeNode deleteRecursive(AVLTreeNode node, int value) {
        if (node == null) return null;

        if (value < node.getValue()) {
            node.setLeftChild(deleteRecursive((AVLTreeNode) node.getLeftChild(), value));
        } else if (value > node.getValue()) {
            node.setRightChild(deleteRecursive((AVLTreeNode) node.getRightChild(), value));
        } else {
            if ((node.getLeftChild() == null) || (node.getRightChild() == null)) {
                AVLTreeNode temp = (AVLTreeNode) ((node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild());
                if (temp == null) {
                    node = null;
                } else { // Có 1 con
                    node = temp;
                }
            } else {
                int smallestVal = findSmallestValue((BinaryTreeNode) node.getRightChild());
                node.changeValue(smallestVal);
                node.setRightChild(deleteRecursive((AVLTreeNode) node.getRightChild(), smallestVal));
            }
        }
        if (node == null) return null;
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        return rebalance(node);
    }
}