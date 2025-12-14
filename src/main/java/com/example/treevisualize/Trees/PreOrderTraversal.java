package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;

import java.util.ArrayList;
import java.util.List;

public class PreOrderTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Node root) {
        List<Node> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    private void preOrderRecursive(Node node, List<Node> result) {
        if (node == null) {
            return;
        }
        result.add(node);
        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode binaryNode = (BinaryTreeNode) node;
            preOrderRecursive(binaryNode.getLeftChild(), result);
            preOrderRecursive(binaryNode.getRightChild(), result);
        }
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode gNode = (GeneralTreeNode) node;
            GeneralTreeNode child = gNode.getLeftMostChild();
            while (child != null) {
                preOrderRecursive(child, result);
                child = child.getRightSibling();
            }
        }
    }
}