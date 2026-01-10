package com.example.treevisualize.Controller.SubSystems.Recorder;

import com.example.treevisualize.View.Visualizer.TreeSnapShot;
import java.util.List;

public interface RecorderStrategy {
    void startRecording();
    void reset();
    List<TreeSnapShot> stopRecording();
}