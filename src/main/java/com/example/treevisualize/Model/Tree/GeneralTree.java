package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.GeneralTreeNode;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent; 
import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.Model.Node.NodeStatus;


public class GeneralTree extends Tree {
    public GeneralTree(){
        super();
    }
    // --- Insert ---
    @Override
    public void insert(int value) {
        notifyEvent(StandardEvent.START, root);
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            root = new GeneralTreeNode(value);
            notifyEvent(StandardEvent.INSERT_SUCCESS, root);
            notifyStructureChanged();
        } else {
            throw new UnsupportedOperationException("General Tree insert requires Parent ID. Use insert(parent, child).");
        }
    }
    @Override
    public void insert(int parentVal, int childVal) {
        // If tree is empty, redirect to root creation for convenience/safety
        if (root == null) {
            insert(childVal);
            return;
        }

        notifyEvent(StandardEvent.START, root);
        GeneralTreeNode parent = (GeneralTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Cannot find parent node with value: " + parentVal);
        } else {
            notifyEvent(StandardEvent.FOUND_INSERT_POS, parent);
            parent.addChild(new GeneralTreeNode(childVal));
            notifyEvent(StandardEvent.INSERT_SUCCESS, null);
            notifyStructureChanged();
        }
    }

    // --- Search ---

    @Override
    public Node search(int value) {
        notifyEvent(StandardEvent.START, root);
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            return null;
        }
        return searchUIRecursive(root, value);
    }

    private Node searchUIRecursive(Node node, int value) {
        if (node == null) return null;

        // 1. Highlight the current node being inspected
        node.changeStatus(NodeStatus.ACTIVE);
        notifyNodeChanged(node);
        notifyEvent(StandardEvent.SEARCH_CHECK, node);

        // 2. Check if this is the target
        if (node.getValue() == value) {
            notifyEvent(StandardEvent.SEARCH_FOUND, node);
            // Restore status before returning the found node
            node.changeStatus(NodeStatus.NORMAL);
            notifyNodeChanged(node);
            return node;
        }

        // 3. Recurse into children if not found at current node
        for (Node child : node.getChildren()) {
            notifyEvent(StandardEvent.SEARCH_RECURSE, child);
            Node result = searchUIRecursive(child, value);
            
            if (result != null) {
                // If found in a subtree, restore current node status and bubble up
                node.changeStatus(NodeStatus.NORMAL);
                notifyNodeChanged(node);
                return result;
            }
        }

        // 4. Backtracking: If not found in this branch, restore status and return null
        node.changeStatus(NodeStatus.NORMAL);
        notifyNodeChanged(node);
        return null;
    }
    // --- Delete ---
    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);
        if (root == null) return;
        GeneralTreeNode targetNode = (GeneralTreeNode) search(value);

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

        GeneralTreeNode parent = (GeneralTreeNode) targetNode.getParent();
        if (parent != null) {
            parent.removeChild(new GeneralTreeNode(value));
            notifyStructureChanged();
        } else {
            notifyError("Structural error: Node " + value + " lost connection to parent.");
        }
    }
}