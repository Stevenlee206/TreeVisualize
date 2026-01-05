package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Description.TreeType;
import com.example.treevisualize.View.Layout.ReingoldTilfordLayout;
import com.example.treevisualize.View.Layout.TreeLayout;
import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.Model.Tree.Tree;
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
        this.layoutStrategy = new ReingoldTilfordLayout(type.getAlignmentStrategy()); // Sử dụng thuật toán chống tràn
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
    // 1. [MỚI] Public hàm này để AnimationController/Transition tính toán điểm đến
    // Nó gọi xuống Strategy ReingoldTilford mà bạn đã viết
    public Map<Node, Point2D> calculateLayout(Node root) {
        return layoutStrategy.calculateLayout(root);
    }

    // 2. [MỚI] Hàm vẽ đặc biệt cho Animation (Vẽ dựa trên Map toạ độ động)
    // structureRoot: Cấu trúc cây (để biết ai nối với ai)
    // currentPositions: Map<Giá trị Node, Toạ độ> (Vị trí hiện tại của node đang bay)
    public void renderAnimation(Node structureRoot, Map<Integer, Point2D> currentPositions) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Xoá màn hình & Reset
        gc.setTransform(new Affine());
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (structureRoot == null) return;

        // Vẫn áp dụng Camera (Pan/Zoom) để người dùng có thể soi khi node đang bay
        gc.translate(translateX, translateY);
        gc.scale(scale, scale);

        renderEdgesCustom(gc, structureRoot, currentPositions);
        renderNodesCustom(gc, structureRoot, currentPositions);
    }
    // --- Các hàm Helper vẽ dựa trên Map toạ độ ---

    private void renderEdgesCustom(GraphicsContext gc, Node node, Map<Integer, Point2D> positions) {
        if (node == null || !positions.containsKey(node.getValue())) return;
        Point2D parentPos = positions.get(node.getValue());

        for (Node child : node.getChildren()) {
            if (positions.containsKey(child.getValue())) {
                Point2D childPos = positions.get(child.getValue());
                // Vẽ dây nối
                gc.setStroke(javafx.scene.paint.Color.BLACK);
                gc.setLineWidth(1.5);
                gc.strokeLine(parentPos.getX(), parentPos.getY(), childPos.getX(), childPos.getY());

                renderEdgesCustom(gc, child, positions);
            }
        }
    }

    private void renderNodesCustom(GraphicsContext gc, Node node, Map<Integer, Point2D> positions) {
        if (node == null || !positions.containsKey(node.getValue())) return;
        Point2D pos = positions.get(node.getValue());

        // Gọi Renderer (Strategy vẽ hình tròn)
        double radius = 20;
        type.getRenderer().drawNode(gc, node, pos.getX(), pos.getY(), radius);

        for (Node child : node.getChildren()) {
            renderNodesCustom(gc, child, positions);
        }
    }
}