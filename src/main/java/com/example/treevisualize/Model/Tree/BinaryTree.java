package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.BinaryTreeNode;
import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree extends Tree {

    public BinaryTree() {
        super();
    }

    @Override
    public void insert(int value) {
        notifyEvent(StandardEvent.START, root);

        BinaryTreeNode newNode = new BinaryTreeNode(value);

        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            root = newNode;
            notifyEvent(StandardEvent.INSERT_SUCCESS, root);
            notifyStructureChanged();
            return;
        }

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add((BinaryTreeNode) root);

        while (!queue.isEmpty()) {
            BinaryTreeNode current = queue.poll();

            if (current.getLeftChild() == null) {
                notifyEvent(StandardEvent.FOUND_INSERT_POS, current);
                current.setLeftChild(newNode); 
                notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
                notifyStructureChanged();
                return;
            } else {
                notifyEvent(StandardEvent.GO_LEFT, current);
                queue.add(current.getLeftChild());
            }

            if (current.getRightChild() == null) {
                notifyEvent(StandardEvent.FOUND_INSERT_POS, current);
                current.setRightChild(newNode);
                notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
                notifyStructureChanged();
                return;
            } else {
                notifyEvent(StandardEvent.GO_RIGHT, current);
                queue.add(current.getRightChild());
            }
        }
    }

    public void insert(int parentVal, int childVal) {
        notifyEvent(StandardEvent.START, root);

        if (root == null) {
            root = new BinaryTreeNode(parentVal);
            notifyEvent(StandardEvent.INSERT_SUCCESS, root);
        }

        BinaryTreeNode parent = (BinaryTreeNode) search(parentVal);
        if (parent == null) {
            notifyError("Cannot find parent node with value: " + parentVal);
            return;
        }

        notifyEvent(StandardEvent.FOUND_INSERT_POS, parent);

        BinaryTreeNode child = new BinaryTreeNode(childVal);

        if (parent.getLeftChild() == null) {
            parent.setLeftChild(child);
        } else if (parent.getRightChild() == null) {
            parent.setRightChild(child);
        } else {
            notifyError("Node " + parentVal + " already has 2 children. Cannot insert more!");
            return;
        }

        notifyEvent(StandardEvent.INSERT_SUCCESS, child);
        notifyStructureChanged();
    }

    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);

        if (root == null) return;

        BinaryTreeNode targetNode = (BinaryTreeNode) search(value);
        if (targetNode == null) {
            notifyError("Cannot delete: value " + value + " not found.");
            return;
        }

        notifyEvent(StandardEvent.DELETE_SUCCESS, targetNode);

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
            notifyError("Structural error: Node " + value + " has no parent.");
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
            notifyEvent(StandardEvent.COMPARE_LESS, node);
            return node;
        }

        notifyEvent(StandardEvent.GO_LEFT, node);
        BinaryTreeNode leftResult = searchRecursive(node.getLeftChild(), value);
        if (leftResult != null) return leftResult;

        notifyEvent(StandardEvent.GO_RIGHT, node);
        return searchRecursive(node.getRightChild(), value);
    }
}
