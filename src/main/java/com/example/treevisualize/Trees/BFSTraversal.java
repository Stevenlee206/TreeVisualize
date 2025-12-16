package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Node root) {
        List<Node> result = new ArrayList<>();
        if (root == null) return result;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(current);
            if (current instanceof BinaryTreeNode) {
                BinaryTreeNode bNode = (BinaryTreeNode) current;
                if (bNode.getLeftChild() != null) {
                    queue.add(bNode.getLeftChild());
                }
                if (bNode.getRightChild() != null) {
                    queue.add(bNode.getRightChild());
                }
            }
            else if (current instanceof GeneralTreeNode) {
                GeneralTreeNode gNode = (GeneralTreeNode) current;
                GeneralTreeNode child = gNode.getLeftMostChild();
                while (child != null) {
                    queue.add(child);
                    child = child.getRightSibling();
                }
            }
        }
        return result;
    }
}