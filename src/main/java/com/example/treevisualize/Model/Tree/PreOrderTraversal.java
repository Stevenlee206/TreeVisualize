package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.BinaryTreeNode;
import com.example.treevisualize.Model.Node.GeneralTreeNode;
import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.View.Visualizer.Events.TraversalEvent;

import java.util.ArrayList;
import java.util.List;

public class PreOrderTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Tree tree, Node root) {
        List<Node> result = new ArrayList<>();
        // Báo hiệu bắt đầu
        tree.notifyEvent(TraversalEvent.START, root);

        preOrderRecursive(tree, root, result);

        return result;
    }

    private void preOrderRecursive(Tree tree, Node node, List<Node> result) {
        if (node == null) {
            return;
        }

        // [QUAN TRỌNG] Thăm nút (VISIT) trước khi đệ quy
        result.add(node);
        tree.notifyEvent(TraversalEvent.VISIT, node); // Highlight dòng result.add(node)

        // Đệ quy
        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode binaryNode = (BinaryTreeNode) node;
            preOrderRecursive(tree, binaryNode.getLeftChild(), result);
            preOrderRecursive(tree, binaryNode.getRightChild(), result);
        }
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode gNode = (GeneralTreeNode) node;
            GeneralTreeNode child = gNode.getLeftMostChild();
            while (child != null) {
                preOrderRecursive(tree, child, result);
                child = child.getRightSibling();
            }
        }
    }
}