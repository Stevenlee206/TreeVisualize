package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.GeneralTreeNode;
public class GeneralTree extends Tree {
    public GeneralTree(){
        super();
    }
    public void insert(int parentVal, int childVal) {
        GeneralTreeNode parent = (GeneralTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Cannot find parent node with value: " + parentVal);
        } else {
            parent.addChild(new GeneralTreeNode(childVal));
            notifyStructureChanged();
        }
    }

    @Override
    public Node search(int value){
        if (root == null) return null;
        return searchRecursive((GeneralTreeNode) root, value);
    }

    @Override
    public void delete(int value) {
        if (root == null) {
            return;
        }
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
            notifyStructureChanged();
        } else {
            notifyError("Structural error: com.example.treevisualize.Node " + value + " lost connection to its parent.");
        }
    }

    private GeneralTreeNode searchRecursive(GeneralTreeNode node, int value) {
        if (node == null) return null;

        if (node.getValue() == value) {
            return node;
        }

        GeneralTreeNode foundInChild = searchRecursive(node.getLeftMostChild(), value);
        if (foundInChild != null) {
            return foundInChild;
        }

        return searchRecursive(node.getRightSibling(), value);
        }

        @Override
        public void insert(int value) {
            if (root == null) {
                root = new GeneralTreeNode(value);
                notifyStructureChanged();
            } else {
                notifyError("A general tree requires a Parent ID. Please use the function: Insert(parent, child)."
);
            }
        }

    public int getHeight() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return 0;
        if (node instanceof GeneralTreeNode) {
            var g = (GeneralTreeNode) node;

            int maxChildHeight = 0;
            Node child = g.getLeftMostChild();

            while (child != null) {
                maxChildHeight = Math.max(maxChildHeight, height(child));
                child = ((GeneralTreeNode) child).getRightSibling();
            }
            return 1 + maxChildHeight;
        }

        return 1;
    }

    public int getNodeCount() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;
        if (node instanceof GeneralTreeNode) {
            var g = (GeneralTreeNode) node;
            return 1 + countNodes(g.getLeftMostChild())
                    + countNodes(g.getRightSibling());
        }

        return 1;
    }
}
