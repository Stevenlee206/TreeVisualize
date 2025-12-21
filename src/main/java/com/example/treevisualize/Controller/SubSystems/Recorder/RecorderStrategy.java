package com.example.treevisualize.Controller.SubSystems.Recorder;

import com.example.treevisualize.View.Visualizer.TreeSnapShot;
import java.util.List;

public interface RecorderStrategy {
    void startRecording();
    List<TreeSnapShot> stopRecording();
}