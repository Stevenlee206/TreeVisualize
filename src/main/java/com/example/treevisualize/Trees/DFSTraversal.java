package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Visualizer.Events.TraversalEvent;

import java.util.ArrayList;
import java.util.List;

public class DFSTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Tree tree, Node root) {
        List<Node> result = new ArrayList<>();
        tree.notifyEvent(TraversalEvent.START, root);
        dfsRecursive(tree, root, result);
        tree.notifyEvent(TraversalEvent.FINISHED, null);
        return result;
    }

    private void dfsRecursive(Tree tree, Node node, List<Node> result) {
        if (node == null) return;

        // Highlight: Visit
        result.add(node);
        tree.notifyEvent(TraversalEvent.VISIT, node);

        // Đệ quy (Không highlight explicit, để flow tự nhiên)
        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode bNode = (BinaryTreeNode) node;
            dfsRecursive(tree, bNode.getLeftChild(), result);
            dfsRecursive(tree, bNode.getRightChild(), result);
        }
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode gNode = (GeneralTreeNode) node;
            GeneralTreeNode child = gNode.getLeftMostChild();
            while (child != null) {
                dfsRecursive(tree, child, result);
                child = child.getRightSibling();
            }
        }
    }
}