package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;

import java.util.ArrayList;
import java.util.List;

public class PostOrderTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Node root) {
        List<Node> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    private void postOrderRecursive(Node node, List<Node> result) {
        if (node == null) return;
        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode bNode = (BinaryTreeNode) node;
            postOrderRecursive(bNode.getLeftChild(), result);
            postOrderRecursive(bNode.getRightChild(), result);
            result.add(node);
        }
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode gNode = (GeneralTreeNode) node;
            GeneralTreeNode child = gNode.getLeftMostChild();
            while (child != null) {
                postOrderRecursive(child, result);
                child = child.getRightSibling();
            }
            result.add(node);
        }
        else {
            result.add(node);
        }
    }
}