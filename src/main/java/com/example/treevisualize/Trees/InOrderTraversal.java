package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Visualizer.Events.TraversalEvent;
import java.util.ArrayList;
import java.util.List;

public class InOrderTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Tree tree, Node root) {
        List<Node> result = new ArrayList<>();
        tree.notifyEvent(TraversalEvent.START, root);
        inOrderRecursive(tree, root, result);
        tree.notifyEvent(TraversalEvent.FINISHED, null);
        return result;
    }

    private void inOrderRecursive(Tree tree, Node node, List<Node> result) {
        if (node == null) return;

        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode bNode = (BinaryTreeNode) node;
            inOrderRecursive(tree, bNode.getLeftChild(), result);

            // Highlight: Visit (Nằm giữa)
            result.add(bNode);
            tree.notifyEvent(TraversalEvent.VISIT, bNode);

            inOrderRecursive(tree, bNode.getRightChild(), result);
        }
        // Logic General Tree rút gọn...
        else {
            result.add(node);
            tree.notifyEvent(TraversalEvent.VISIT, node);
        }
    }
}