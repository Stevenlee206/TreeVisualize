package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public class TreeTransition extends Transition {
    private final TreeVisualizer visualizer;
    private final Node endStructure; // Cấu trúc đích

    // Map lưu vị trí: Key là GIÁ TRỊ (Value) của node (vì Node object bị clone)
    private final Map<Integer, Point2D> startLayout = new HashMap<>();
    private final Map<Integer, Point2D> endLayout = new HashMap<>();

    public TreeTransition(TreeVisualizer visualizer, Node startRoot, Node endRoot, Duration duration) {
        this.visualizer = visualizer;
        this.endStructure = endRoot;
        setCycleDuration(duration);

        // 1. Dùng ReingoldTilford tính layout cho Snapshot CŨ (Điểm xuất phát)
        if (startRoot != null) {
            visualizer.calculateLayout(startRoot)
                    .forEach((n, p) -> startLayout.put(n.getValue(), p));
        }

        // 2. Dùng ReingoldTilford tính layout cho Snapshot MỚI (Điểm đích)
        if (endRoot != null) {
            visualizer.calculateLayout(endRoot)
                    .forEach((n, p) -> endLayout.put(n.getValue(), p));
        }

        // 3. Xử lý node mới (Insert): Cho nó xuất hiện ngay tại đích
        for (Integer val : endLayout.keySet()) {
            if (!startLayout.containsKey(val)) {
                startLayout.put(val, endLayout.get(val));
            }
        }
    }

    @Override
    protected void interpolate(double frac) {
        // frac chạy từ 0.0 đến 1.0 theo thời gian
        Map<Integer, Point2D> currentPos = new HashMap<>();

        for (Integer val : endLayout.keySet()) {
            Point2D start = startLayout.get(val);
            Point2D end = endLayout.get(val);

            if (start != null && end != null) {
                // Công thức Lerp (Linear Interpolation)
                double x = start.getX() + (end.getX() - start.getX()) * frac;
                double y = start.getY() + (end.getY() - start.getY()) * frac;
                currentPos.put(val, new Point2D(x, y));
            }
        }

        // Vẽ frame trung gian
        visualizer.renderAnimation(endStructure, currentPos);
    }
}