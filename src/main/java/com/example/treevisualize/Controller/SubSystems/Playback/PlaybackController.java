package com.example.treevisualize.Controller.SubSystems.Playback;

import com.example.treevisualize.Visualizer.TreeSnapShot;

public interface PlaybackController {
    // Các lệnh điều khiển Media
    void play();
    void pause();
    void setSpeed(double multiplier);

    // Các lệnh hiển thị
    void renderInstant(TreeSnapShot snapshot);
    void renderTransition(TreeSnapShot start, TreeSnapShot end);

    // Sự kiện callback
    void setOnTickRequest(Runnable callback);
}