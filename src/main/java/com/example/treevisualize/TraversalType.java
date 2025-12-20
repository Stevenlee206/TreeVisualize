package com.example.treevisualize;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.PseudoCodeStore.Traversal.*;
import com.example.treevisualize.Trees.*;

public enum TraversalType {
    BFS("BFS (Level Order)", new BFSPCode(), new BFSTraversal()),
    DFS("DFS (Pre-Order)",   new DFSPCode(), new DFSTraversal()),
    PRE_ORDER("Pre-Order",new PreOrderPCode(),new PreOrderTraversal()),
    IN_ORDER("In-Order",     new InOrderPCode(), new InOrderTraversal()),
    POST_ORDER("Post-Order", new PostOrderPCode(), new PostOrderTraversal());

    private final String label;
    private final PseudoCodeStrategy pseudoCode;
    private final TraversalStrategy algorithm;

    TraversalType(String label, PseudoCodeStrategy code, TraversalStrategy algo) {
        this.label = label;
        this.pseudoCode = code;
        this.algorithm = algo;
    }

    public PseudoCodeStrategy getPseudoCode() { return pseudoCode; }
    public TraversalStrategy getAlgorithm() { return algorithm; }

    @Override
    public String toString() { return label; }
}