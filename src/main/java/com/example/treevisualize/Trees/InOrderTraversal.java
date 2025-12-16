package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;
import java.util.ArrayList;
import java.util.List;

public class InOrderTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Node root) {
        List<Node> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(Node node, List<Node> result) {
        if (node == null) {
            return;
        }
        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode binaryNode = (BinaryTreeNode) node;
            inOrderRecursive(binaryNode.getLeftChild(), result);
            result.add(binaryNode);
            inOrderRecursive(binaryNode.getRightChild(), result);
        }
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode gNode = (GeneralTreeNode) node;
            GeneralTreeNode firstChild = gNode.getLeftMostChild();
            if (firstChild != null) {
                inOrderRecursive(firstChild, result);
            }
            result.add(gNode);
            if (firstChild != null) {
                GeneralTreeNode currentSibling = firstChild.getRightSibling();
                while (currentSibling != null) {
                    inOrderRecursive(currentSibling, result);
                    currentSibling = currentSibling.getRightSibling();
                }
            }
        }
        else {
            result.add(node);
        }
    }
}