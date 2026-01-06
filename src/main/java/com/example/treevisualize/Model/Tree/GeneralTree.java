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
        GeneralTreeNode parent = (GeneralTreeNode) search(root, parentVal);

        if (parent == null) {
            notifyError("Cannot find parent node with value: " + parentVal);
        } else {
            notifyEvent(StandardEvent.FOUND_INSERT_POS, parent);
            parent.addChild(new GeneralTreeNode(childVal));
            notifyEvent(StandardEvent.INSERT_SUCCESS, null);
            notifyStructureChanged();
        }
    }

  

    // --- Delete ---
    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);
        if (root == null) return;
        GeneralTreeNode targetNode = (GeneralTreeNode) search(root, value);

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