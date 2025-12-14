package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;

public class TreeSnapShot {
    private Node rootCopy;
    private int pseudoLineIndex;
    private String statusMessage;

    public Node getRootCopy() {
        return rootCopy;
    }

    public int getPseudoLineIndex() {
        return pseudoLineIndex;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public TreeSnapShot(Node realRoot,int lineIdx,String message){
        if (realRoot != null) {
            this.rootCopy = realRoot.copy();
        } else {
            this.rootCopy = null;
        }

        this.pseudoLineIndex = lineIdx;
        this.statusMessage = message;
    }
}
