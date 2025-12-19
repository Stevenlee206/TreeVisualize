package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Description.TreeType;
import com.example.treevisualize.Layout.CompactTreeLayout;
import com.example.treevisualize.Layout.TreeLayout;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Trees.Tree;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Affine;
import java.util.Map;

public class TreeVisualizer {
    private final Tree tree;
    private final Canvas canvas;
    private final TreeType type;
    private final TreeLayout layoutStrategy; // Layout Engine

    // Camera State
    private double scale = 1.0;
    private double translateX = 400;
    private double translateY = 50;

    public TreeVisualizer(Tree tree, TreeType type, Canvas canvas) {
        this.tree = tree;
        this.type = type;
        this.canvas = canvas;
        this.layoutStrategy = new CompactTreeLayout(); // Sử dụng thuật toán chống tràn
    }

    public void setScale(double scale) {
        this.scale = scale;
        render();
    }

    public void pan(double deltaX, double deltaY) {
        this.translateX += deltaX;
        this.translateY += deltaY;
        render();
    }

    public void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 1. Reset & Clear
        gc.setTransform(new Affine());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (tree.getRoot() == null) return;

        // 2. Tính toán vị trí (Layout)
        Map<Node, Point2D> positions = layoutStrategy.calculateLayout(tree.getRoot());

        // 3. Áp dụng Camera
        gc.translate(translateX, translateY);
        gc.scale(scale, scale);

        // 4. Vẽ Dây (Edges) - Visualizer phụ trách
        renderEdges(gc, tree.getRoot(), positions);

        // 5. Vẽ Node - Renderer phụ trách
        renderNodes(gc, tree.getRoot(), positions);
    }

    private void renderEdges(GraphicsContext gc, Node node, Map<Node, Point2D> positions) {
        if (node == null || !positions.containsKey(node)) return;
        Point2D parentPos = positions.get(node);

        for (Node child : node.getChildren()) {
            if (positions.containsKey(child)) {
                Point2D childPos = positions.get(child);
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.setLineWidth(1.5);
                gc.strokeLine(parentPos.getX(), parentPos.getY(), childPos.getX(), childPos.getY());
                renderEdges(gc, child, positions);
            }
        }
    }

    private void renderNodes(GraphicsContext gc, Node node, Map<Node, Point2D> positions) {
        if (node == null || !positions.containsKey(node)) return;
        Point2D pos = positions.get(node);

        // [QUAN TRỌNG] Gọi drawNode thay vì renderChildren
        double radius = 20; // Bán kính chuẩn
        type.getRenderer().drawNode(gc, node, pos.getX(), pos.getY(), radius);

        for (Node child : node.getChildren()) {
            renderNodes(gc, child, positions);
        }
    }
    // Thêm vào TreeVisualizer.java

    // Hàm Zoom tích lũy (dùng cho lăn chuột)
    public void zoom(double factor) {
        this.scale *= factor;
        // Giới hạn zoom để không quá to hoặc quá nhỏ
        if (this.scale < 0.1) this.scale = 0.1;
        if (this.scale > 10.0) this.scale = 10.0;
        render();
    }
}