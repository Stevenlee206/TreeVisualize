package com.example.treevisualize.View.Layout;

import com.example.treevisualize.Model.Node.Node;
import javafx.geometry.Point2D;

import java.util.Map;

public interface TreeLayout {
    Map<Node, Point2D> calculateLayout(Node root);
}
