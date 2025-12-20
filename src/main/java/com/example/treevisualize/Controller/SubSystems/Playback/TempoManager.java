package com.example.treevisualize.Controller.SubSystems.Playback;

import javafx.util.Duration;

public class TempoManager {
    private final double BASE_SPEED_MS = 1000.0; // Tốc độ chuẩn (1 giây)
    private double multiplier = 1.0;

    public void setSpeedMultiplier(double multiplier) {
        // Giới hạn tốc độ để không bị lỗi (tối thiểu 0.1x, tối đa tùy ý)
        if (multiplier <= 0.1) multiplier = 0.1;
        this.multiplier = multiplier;
    }

    // Thời gian nghỉ giữa các bước (Delay)
    public Duration getStepDelay() {
        return Duration.millis(BASE_SPEED_MS / multiplier);
    }

    // Thời gian bay của Animation (Transition)
    // Thường ngắn hơn StepDelay một chút để tạo cảm giác "nghỉ" cho mắt
    public Duration getTransitionDuration() {
        double totalMs = BASE_SPEED_MS / multiplier;
        // Dành 80% thời gian để bay, 20% thời gian để người dùng nhìn kết quả
        // Nhưng tối đa chỉ bay 1 giây thôi cho đỡ buồn ngủ
        return Duration.millis(Math.min(totalMs * 0.8, 1000));
    }
}
