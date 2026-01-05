package com.example.treevisualize.Controller.SubSystems.Playback.Renderer;

import com.example.treevisualize.View.Visualizer.TreeSnapShot;
import javafx.util.Duration;

public interface MotionRenderer {
    // Vẽ hiệu ứng chuyển cảnh từ Start -> End
    void playTransition(TreeSnapShot start, TreeSnapShot end, Duration duration, Runnable onFinished);

    // Kiểm tra xem có đang bận vẽ không
    boolean isBusy();

    // Dừng vẽ ngay lập tức
    void stop();
}