package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.Node;

import java.util.List;

public interface TraversalStrategy {
    List<Node> traverse(Tree tree,Node root);
}