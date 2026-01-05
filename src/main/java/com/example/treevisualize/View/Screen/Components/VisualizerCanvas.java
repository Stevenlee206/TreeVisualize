package com.example.treevisualize.View.Screen.Components;

import com.example.treevisualize.View.Visualizer.TreeVisualizer;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

public class VisualizerCanvas extends Pane {
    private final Canvas canvas;
    private TreeVisualizer currentVisualizer;
    private final double[] lastMouse = new double[2]; // Lưu vị trí chuột để Pan

    public VisualizerCanvas(double width, double height) {
        this.canvas = new Canvas(width, height);
        this.getChildren().add(canvas);
        this.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7;");

        setupCameraControls();
    }

    public Canvas getCanvas() { return canvas; }

    // Cập nhật Visualizer mới mỗi khi reset hệ thống
    public void bindVisualizer(TreeVisualizer visualizer) {
        this.currentVisualizer = visualizer;
    }

    private void setupCameraControls() {
        // 1. Xử lý Pan (Kéo thả)
        canvas.setOnMousePressed(event -> {
            lastMouse[0] = event.getX();
            lastMouse[1] = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            if (currentVisualizer != null) {
                double deltaX = event.getX() - lastMouse[0];
                double deltaY = event.getY() - lastMouse[1];
                currentVisualizer.pan(deltaX, deltaY);
            }
            lastMouse[0] = event.getX();
            lastMouse[1] = event.getY();
        });

        // 2. Xử lý Zoom (Lăn chuột)
        canvas.setOnScroll(event -> {
            if (currentVisualizer != null) {
                double zoomFactor = 1.05;
                if (event.getDeltaY() < 0) zoomFactor = 1 / zoomFactor;
                 currentVisualizer.zoom(zoomFactor);
            }
            event.consume();
        });
    }
}
