package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;

import com.example.treevisualize.Node.GeneralTreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneralTree extends Tree {

    public GeneralTree() {
        super();
    }

    public void insert(int parentVal, int childVal) {
        GeneralTreeNode parent = (GeneralTreeNode) search(parentVal);
        if (parent == null) {
            notifyError("Cannot find parent node with value: " + parentVal);
            return;
        }

        parent.addChild(new GeneralTreeNode(childVal));
        notifyStructureChanged();
    }

    @Override
    public void insert(int value) {
        if (root == null) {
            root = new GeneralTreeNode(value);
            notifyStructureChanged();
        } else {
            throw new UnsupportedOperationException(
                "GeneralTree.insert(value) is not supported. Use insert(parent, child)."
            );
        }
    }
    
    @Override
    public void insertRandom(int value) {
    	if (root == null) {
            root = new GeneralTreeNode(value);
            notifyStructureChanged();
            return;
        }
    	List<GeneralTreeNode> allNodes = new ArrayList<>();
        collectAllNodes(root, allNodes);

        if (allNodes.isEmpty()) {
            notifyError("Cannot insert random node: tree is empty.");
            return;
        }

        GeneralTreeNode randomParent = allNodes.get(
                new Random().nextInt(allNodes.size())
        );

        randomParent.addChild(new GeneralTreeNode(value));
        notifyStructureChanged();
    }
    private void collectAllNodes(Node current, List<GeneralTreeNode> list) {
        if (current instanceof GeneralTreeNode gtNode) {
            list.add(gtNode);
        }
        for (Node child : current.getChildren()) {
            collectAllNodes(child, list);
        }
    }

    @Override
    public Node search(int value) {
        if (root == null) return null;
        return searchRecursive((GeneralTreeNode) root, value);
    }

    private GeneralTreeNode searchRecursive(GeneralTreeNode node, int value) {
        if (node == null) return null;

        if (node.getValue() == value) {
            return node;
        }

        // search children
        GeneralTreeNode found = searchRecursive(
                (GeneralTreeNode) node.getLeftMostChild(), value
        );
        if (found != null) return found;

        // search siblings
        return searchRecursive(
                (GeneralTreeNode) node.getRightSibling(), value
        );
    }


    @Override
    public void delete(int value) {
        if (root == null) return;

        GeneralTreeNode target = (GeneralTreeNode) search(value);

        if (target == null) {
            notifyError("Cannot delete: value " + value + " not found.");
            return;
        }

        if (target == root) {
            clear();
            notifyStructureChanged();
            return;
        }

        GeneralTreeNode parent = (GeneralTreeNode) target.getParent();
        if (parent == null) {
            notifyError("Structural error: parent not found for node " + value);
            return;
        }

        parent.removeChild(target);
        notifyStructureChanged();
    }


}
