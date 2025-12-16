package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree extends Tree{
    public BinaryTree() {
        super();
    }

    @Override
    public void insert(int value) {
        BinaryTreeNode newNode = new BinaryTreeNode(value);
        if (root == null) {
            root = newNode;
            notifyStructureChanged();
            return;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add((BinaryTreeNode) root);

        while (!queue.isEmpty()) {
            BinaryTreeNode current = queue.poll();
            if (current.getLeftChild() == null) {
                current.setLeftChild(newNode);
                notifyStructureChanged();
                return;
            } else {
                queue.add(current.getLeftChild());
            }
            if (current.getRightChild() == null) {
                current.setRightChild(newNode);
                notifyStructureChanged();
                return;
            } else {
                queue.add(current.getRightChild());
            }
        }
    }

    @Override
    public void delete(int value) {
        if (root == null) return;
        BinaryTreeNode targetNode = (BinaryTreeNode) search(value);

        if (targetNode == null) {
            notifyError("Cannot delete: value " + value + " not found.");
            return;
        }

        if (targetNode == root) {
            clear();
            notifyStructureChanged();
            return;
        }
        BinaryTreeNode parent = (BinaryTreeNode) targetNode.getParent();

        if (parent != null) {
            try {
                parent.removeChild(targetNode);
                notifyStructureChanged();
            } catch (Exception e) {
                notifyError("Error while deleting: " + e.getMessage());
            }
        } else {
            notifyError("Structural error: com.example.treevisualize.Node " + value + " doesn't have parent (is not Root).");
        }
    }

    @Override
    public Node search(int value) {
        if (root == null) return null;
        return searchRecursive((BinaryTreeNode) root, value);
    }

    private BinaryTreeNode searchRecursive(BinaryTreeNode node, int value) {
        if (node == null) return null;

        if (node.getValue() == value) {
            return node;
        }
        BinaryTreeNode leftResult = searchRecursive(node.getLeftChild(), value);
        if (leftResult != null) {
            return leftResult;
        }
        return searchRecursive(node.getRightChild(), value);
    }

    public void insert(int parentVal, int childVal) {
        BinaryTreeNode parent = (BinaryTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Cannot find parent node with value: " + parentVal);
            return;
        }
        if (parent.getLeftChild() == null) {
            parent.setLeftChild(new BinaryTreeNode(childVal));
            notifyStructureChanged();
        }
        else if (parent.getRightChild() == null) {
            parent.setRightChild(new BinaryTreeNode(childVal));
            notifyStructureChanged();
        }
        else {
            notifyError("Node " + parentVal + " already has 2 children. Cannot insert more!");
        }
    }

    public int getHeight() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return 0;
        // Binary Tree / BST / RBT
        if (node instanceof BinaryTreeNode) {
            var b = (BinaryTreeNode) node;
            return 1 + Math.max(
                    height(b.getLeftChild()),
                    height(b.getRightChild())
            );
        }
        return 1;
    }

    public int getNodeCount() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;

        // Binary Tree / BST / RBT
        if (node instanceof BinaryTreeNode) {
            var b = (BinaryTreeNode) node;
            return 1 + countNodes(b.getLeftChild()) + countNodes(b.getRightChild());
        }
        return 1;
    }
}
