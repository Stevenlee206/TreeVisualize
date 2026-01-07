package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.GeneralTreeNode;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent; 

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
    public com.example.treevisualize.Model.Node.Node search(int value){
        if (root == null) return null;
        return searchRecursive((GeneralTreeNode) root, value);
    }

    private GeneralTreeNode searchRecursive(GeneralTreeNode node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return node;
      //search children
        GeneralTreeNode foundInChild = searchRecursive(node.getLeftMostChild(), value);
        if (foundInChild != null) return foundInChild;
      //search siblings
        return searchRecursive(node.getRightSibling(), value);
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

        if (targetNode == root) {
            clear();
            notifyStructureChanged();
            return;
        }

        GeneralTreeNode parent = (GeneralTreeNode) targetNode.getParent();
        if (parent != null) {
            parent.removeChild(new GeneralTreeNode(value));
            notifyEvent(StandardEvent.DELETE_SUCCESS, targetNode);
            notifyStructureChanged();
        } else {
            notifyError("Structural error: Node " + value + " lost connection to parent.");
        }
    }
}