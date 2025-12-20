package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Visualizer.Events.TraversalEvent;

import java.util.ArrayList;
import java.util.List;

public class PostOrderTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Tree tree, Node root) {
        List<Node> result = new ArrayList<>();
        tree.notifyEvent(TraversalEvent.START, root);

        postOrderRecursive(tree, root, result);

        tree.notifyEvent(TraversalEvent.FINISHED, null);
        return result;
    }

    private void postOrderRecursive(Tree tree, Node node, List<Node> result) {
        if (node == null) return;

        // 1. Đệ quy trước
        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode bNode = (BinaryTreeNode) node;
            postOrderRecursive(tree, bNode.getLeftChild(), result);
            postOrderRecursive(tree, bNode.getRightChild(), result);
        }
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode gNode = (GeneralTreeNode) node;
            GeneralTreeNode child = gNode.getLeftMostChild();
            while (child != null) {
                postOrderRecursive(tree, child, result);
                child = child.getRightSibling();
            }
        }

        // 2. [QUAN TRỌNG] Thăm nút (VISIT) sau khi đã duyệt xong con
        result.add(node);
        tree.notifyEvent(TraversalEvent.VISIT, node); // Highlight dòng result.add(node)
    }
}