package com.example.treevisualize.Layout;

import com.example.treevisualize.Node.Node;
import javafx.geometry.Point2D;

import java.util.Map;

public interface TreeLayout {
    Map<Node, Point2D> calculateLayout(Node root);
}
