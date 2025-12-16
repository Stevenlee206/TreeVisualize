package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;

import java.util.List;

public interface TraversalStrategy {
    List<Node> traverse(Node root);
}