package com.example.treevisualize.Controller.SubSystems.Playback;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class JavaFXTicker implements Ticker {
    // Không để final nữa vì chúng ta sẽ thay thế nó
    private Timeline timeline;

    // Lưu trữ hành động và duration hiện tại để tái tạo Timeline khi cần
    private Runnable onTickAction;
    private Duration currentDuration;

    // Trạng thái mong muốn (để biết có nên auto-start khi set lại interval không)
    private boolean isRunning = false;

    public JavaFXTicker() {
        // Khởi tạo dummy ban đầu
        createNewTimeline(Duration.millis(1000));
    }

    private void createNewTimeline(Duration duration) {
        // 1. Dọn dẹp cái cũ nếu có
        if (this.timeline != null) {
            this.timeline.stop();
            this.timeline.getKeyFrames().clear(); // Giúp GC dọn dẹp nhanh hơn
            this.timeline = null;
        }

        // 2. Tạo cái mới tinh (Fresh Instance)
        this.timeline = new Timeline();
        this.timeline.setCycleCount(Timeline.INDEFINITE);

        // 3. Cài đặt KeyFrame
        this.currentDuration = duration;
        if (duration != null) {
            this.timeline.getKeyFrames().add(new KeyFrame(duration, e -> {
                if (onTickAction != null) onTickAction.run();
            }));
        }
    }

    @Override
    public void setOnTick(Runnable action) {
        this.onTickAction = action;
        // Cần update lại KeyFrame với action mới (Re-create strategy)
        if (currentDuration != null) {
            setInterval(currentDuration);
        }
    }

    @Override
    public void setInterval(Duration duration) {
        // Logic kiểm tra để tránh spam tạo object nếu duration không đổi
        if (currentDuration != null && currentDuration.equals(duration) && timeline != null) {
            return;
        }

        // [FIX TRIỆT ĐỂ] Thay vì sửa timeline cũ, ta tạo timeline mới.
        // Điều này tránh lỗi "SimpleClipInterpolator" vì timeline cũ bị vứt bỏ hoàn toàn,
        // JavaFX sẽ không cố nội suy nó nữa.
        createNewTimeline(duration);

        // Khôi phục trạng thái chạy nếu trước đó đang chạy
        if (isRunning) {
            start();
        }
    }

    @Override
    public void start() {
        isRunning = true;
        if (timeline != null) {
            timeline.play();
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        if (timeline != null) {
            timeline.pause();
        }
    }

    @Override
    public boolean isRunning() {
        // Trả về trạng thái mong muốn logic, hoặc trạng thái thực tế của timeline
        return isRunning && timeline != null && timeline.getStatus() == javafx.animation.Animation.Status.RUNNING;
    }
}