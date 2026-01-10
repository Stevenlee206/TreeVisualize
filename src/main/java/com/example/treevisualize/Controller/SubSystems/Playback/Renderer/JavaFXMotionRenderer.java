package com.example.treevisualize.Controller.SubSystems.Playback.Renderer;

import com.example.treevisualize.Model.Tree.Tree;
import com.example.treevisualize.View.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.View.Visualizer.TreeSnapShot;
import com.example.treevisualize.View.Visualizer.TreeTransition;
import com.example.treevisualize.View.Visualizer.TreeVisualizer;
import javafx.util.Duration;

public class JavaFXMotionRenderer implements MotionRenderer {
    private final Tree tree;
    private final TreeVisualizer visualizer;
    private final PseudoCodeBlock pseudoCode;

    private TreeTransition currentTransition;
    private boolean isAnimating = false;

    public JavaFXMotionRenderer(Tree tree, TreeVisualizer visualizer, PseudoCodeBlock pseudoCode) {
        this.tree = tree;
        this.visualizer = visualizer;
        this.pseudoCode = pseudoCode;
    }

    @Override
    public boolean isBusy() {
        return isAnimating;
    }

    @Override
    public void playTransition(TreeSnapShot start, TreeSnapShot end, Duration duration, Runnable onFinished) {
        this.isAnimating = true;

        // Highlight dòng code của trạng thái đích ngay khi bắt đầu bay
        if (pseudoCode != null) {
            pseudoCode.highlightLine(end.getPseudoLineIndex());
        }

        // Tạo Transition (Sử dụng class bạn đã có)
        currentTransition = new TreeTransition(visualizer, start.getRootCopy(), end.getRootCopy(), duration);

        currentTransition.setOnFinished(e -> {
            this.isAnimating = false;

            // [QUAN TRỌNG] Khi bay xong, phải chốt hạ trạng thái cuối cùng vào Model
            // Để đảm bảo không có sai số tọa độ do animation
            tree.setRoot(end.getRootCopy());
            visualizer.render();

            // Gọi callback báo cáo xong việc
            if (onFinished != null) onFinished.run();
        });

        currentTransition.play();
    }

    @Override
    public void stop() {
        if (currentTransition != null) {
            currentTransition.stop();
        }
        isAnimating = false;
    }
}
