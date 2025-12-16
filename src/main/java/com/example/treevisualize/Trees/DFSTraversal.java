package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;

import java.util.ArrayList;
import java.util.List;

public class DFSTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Node root) {
        List<Node> result = new ArrayList<>();
        dfsRecursive(root, result);
        return result;
    }

    private void dfsRecursive(Node node, List<Node> result) {
        if (node == null) return;
        result.add(node);
        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode bNode = (BinaryTreeNode) node;
            dfsRecursive(bNode.getLeftChild(), result);
            dfsRecursive(bNode.getRightChild(), result);
        }
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode gNode = (GeneralTreeNode) node;
            GeneralTreeNode child = gNode.getLeftMostChild();
            while (child != null) {
                dfsRecursive(child, result);
                child = child.getRightSibling();
            }
        }
    }
}   