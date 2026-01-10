package com.example.treevisualize.Controller.SubSystems.Playback;

import javafx.util.Duration;

public interface Ticker {
    void start();
    void stop();
    boolean isRunning();

    void setInterval(Duration duration);
    void setOnTick(Runnable action);
}
