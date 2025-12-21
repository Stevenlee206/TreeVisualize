package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.BinaryTreeNode;
import com.example.treevisualize.Model.Node.GeneralTreeNode;
import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSTraversal implements TraversalStrategy {
    @Override
    public List<Node> traverse(Tree tree, Node root) {
        List<Node> result = new ArrayList<>();
        if (root == null) return result;

        tree.notifyEvent(TraversalEvent.START, root);

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        // Không highlight bước enqueue (quá nhanh)

        while (!queue.isEmpty()) {
            // 1. Highlight: Lấy ra khỏi hàng đợi
            Node current = queue.poll();
            tree.notifyEvent(TraversalEvent.TAKE_FROM_DS, current);

            // 2. Highlight: Visit (Quan trọng nhất)
            result.add(current);
            tree.notifyEvent(TraversalEvent.VISIT, current);

            // Logic thêm con vào queue (Không highlight)
            if (current instanceof BinaryTreeNode) {
                BinaryTreeNode bNode = (BinaryTreeNode) current;
                if (bNode.getLeftChild() != null) queue.add(bNode.getLeftChild());
                if (bNode.getRightChild() != null) queue.add(bNode.getRightChild());
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

        tree.notifyEvent(TraversalEvent.FINISHED, null);
        return result;
    }
}