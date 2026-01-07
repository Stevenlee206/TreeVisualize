package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.BinaryTreeNode;
import com.example.treevisualize.Model.Node.NodeStatus;
import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent; // Import Event

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree extends Tree { 

    public BinaryTree() {
        super();
    }

    // --- INSERT (Level-Order Traversal) ---
    @Override
    public void insert(int value) {
        notifyEvent(StandardEvent.START, root);
        BinaryTreeNode newNode = new BinaryTreeNode(value);

        // Case: Tree is Empty -> Create Root
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            root = newNode;
            notifyEvent(StandardEvent.INSERT_SUCCESS, root);
            notifyStructureChanged();
            return;
        }

        // Case: Tree exists -> Find next empty spot (Level Order)
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add((BinaryTreeNode) root);

        while (!queue.isEmpty()) {
            BinaryTreeNode current = queue.poll();

            // Left
            if (current.getLeftChild() == null) {
                notifyEvent(StandardEvent.FOUND_INSERT_POS, current);
                current.setLeftChild(newNode);
                notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
                notifyStructureChanged();
                return;
            } else {
                notifyEvent(StandardEvent.GO_LEFT, current);
                queue.add((BinaryTreeNode) current.getLeftChild());
            }

            // Right
            if (current.getRightChild() == null) {
                notifyEvent(StandardEvent.FOUND_INSERT_POS, current);
                current.setRightChild(newNode);
                notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
                notifyStructureChanged();
                return;
            } else {
                notifyEvent(StandardEvent.GO_RIGHT, current);
                queue.add((BinaryTreeNode) current.getRightChild());
            }
        }
    }

    // --- Mode 2: Explicit Parent ---
    @Override
    public void insert(int parentVal, int childVal) {
        if (root == null) {
            insert(childVal); // Redirect to root creation
            return;
        }

        notifyEvent(StandardEvent.START, root);
        BinaryTreeNode parent = (BinaryTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Cannot find parent: " + parentVal);
            return;
        }

        notifyEvent(StandardEvent.FOUND_INSERT_POS, parent);
        if (parent.getLeftChild() == null) {
            parent.setLeftChild(new BinaryTreeNode(childVal));
            notifyEvent(StandardEvent.INSERT_SUCCESS, parent.getLeftChild());
        } else if (parent.getRightChild() == null) {
            parent.setRightChild(new BinaryTreeNode(childVal));
            notifyEvent(StandardEvent.INSERT_SUCCESS, parent.getRightChild());
        } else {
            notifyError("Node " + parentVal + " already has 2 children!");
            return;
        }
        notifyStructureChanged();
    }

    // --- DELETE ---
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
                // Ngắt liên kết với cha
                if (parent.getLeftChild() == targetNode) parent.setLeftChild(null);
                else if (parent.getRightChild() == targetNode) parent.setRightChild(null);

                notifyStructureChanged();
            } catch (Exception e) {
                notifyError("Error while deleting: " + e.getMessage());
            }
        } else {
            notifyError("Structural error: Node " + value + " doesn't have parent (is not Root).");
        }
    }
    // --- SEARCH DFS ---
    @Override
    public Node search(int value) {
        notifyEvent(StandardEvent.START, root);
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            return null;
        }
        return searchUIRecursive((BinaryTreeNode) root, value);
    }

    private BinaryTreeNode searchUIRecursive(BinaryTreeNode node, int value) {
        if (node == null) return null;

        // 1. Highlight current node
        node.changeStatus(NodeStatus.ACTIVE);
        notifyNodeChanged(node);
        notifyEvent(StandardEvent.SEARCH_CHECK, node);

        // 2. Base Case: Found
        if (node.getValue() == value) {
            notifyEvent(StandardEvent.SEARCH_FOUND, node);
            node.changeStatus(NodeStatus.NORMAL);
            notifyNodeChanged(node);
            return node;
        }

        // 3. Recursive Search (DFS logic because it's a simple Binary Tree, not a BST)
        // Highlight going left
        notifyEvent(StandardEvent.SEARCH_RECURSE, node); 
        notifyEvent(StandardEvent.GO_LEFT, node);
        
        BinaryTreeNode leftResult = searchUIRecursive(node.getLeftChild(), value);
        if (leftResult != null) {
            // Restore status while bubbling up
            node.changeStatus(NodeStatus.NORMAL);
            notifyNodeChanged(node);
            return leftResult;
        }

        // Highlight going right
        notifyEvent(StandardEvent.GO_RIGHT, node);
        BinaryTreeNode rightResult = searchUIRecursive(node.getRightChild(), value);
        
        // Final status restoration for this node
        node.changeStatus(NodeStatus.NORMAL);
        notifyNodeChanged(node);
        
        return rightResult;
    }


    // ĐÃ XÓA: getHeight() và getNodeCount()
    // Class cha Tree sẽ tự động xử lý việc này thông qua getChildren() của BinaryTreeNode.
}