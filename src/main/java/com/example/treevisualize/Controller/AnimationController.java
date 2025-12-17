package com.example.treevisualize.Controller;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Trees.*;
import com.example.treevisualize.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.Visualizer.TreeObserver;
import com.example.treevisualize.Visualizer.TreeVisualizer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import com.example.treevisualize.Visualizer.TreeSnapShot;
public class AnimationController {

    private Tree tree;

    private TreeVisualizer visualizer;

    private PseudoCodeBlock pseudoCode;

    private Timeline timeLine;

    private boolean isPlaying;

    private double speed;

    // Danh sách các khung hình (Snapshot) của hoạt cảnh hiện tại
    private List<TreeSnapShot> animationFrames;

    private int currentFrameIndex;

    public AnimationController(Tree tree, TreeVisualizer vis, PseudoCodeBlock pseudoCode) {
        this.tree = tree;
        this.visualizer = vis;
        this.pseudoCode = pseudoCode;

        this.animationFrames = new ArrayList<>();
        this.currentFrameIndex = 0;
        this.isPlaying = false;
        this.speed = 1000.0;

        // Khởi tạo Timeline (vòng lặp vô hạn, nhưng ta sẽ control nó)
        this.speed = 1000.0;
        this.timeLine = new Timeline();
        this.timeLine.setCycleCount(Timeline.INDEFINITE);
    }

    public void play() {
        if (animationFrames.isEmpty() || currentFrameIndex >= animationFrames.size() - 1) {
            return;
        }

        this.isPlaying = true;
        createAnimationSequence();

        this.timeLine.play();
    }

    public void pause() {
        this.isPlaying = false;
        this.timeLine.pause();
    }

    public void stepForward() {
        if (currentFrameIndex < animationFrames.size() - 1) {
            currentFrameIndex++;
            restoreSnapshot(animationFrames.get(currentFrameIndex));
        } else {
            pause();
        }
    }

    public void reset() {
        pause();
        if (!animationFrames.isEmpty()) {
            currentFrameIndex = 0;
            restoreSnapshot(animationFrames.get(0));
        }
    }

    public void setSpeed(double delayMs) {
        this.speed = delayMs;
        if (isPlaying) {
            pause();
            play();
        }
    }

    public void startInsert(int val) {
        prepareRecording();
        tree.insert(val);
        finishRecording();
    }

    public void startDelete(int val) {
        prepareRecording();
        tree.delete(val);
        finishRecording();
    }

    public void startSearch(int val) {
        prepareRecording();
        tree.search(val);
        finishRecording();
    }

    private void createAnimationSequence() {
        timeLine.stop();
        timeLine.getKeyFrames().clear();
        KeyFrame frame = new KeyFrame(Duration.millis(speed), event -> {
            stepForward();
        });

        timeLine.getKeyFrames().add(frame);
    }

    private void restoreSnapshot(TreeSnapShot snapshot) {
        tree.setRoot(snapshot.getRootCopy());
        pseudoCode.highlightLine(snapshot.getPseudoLineIndex());
    }

    private TreeObserver recorder;

    private void prepareRecording() {
        this.animationFrames.clear();
        this.currentFrameIndex = 0;

        captureFrame(-1, "Start");
        recorder = new TreeObserver() {
            @Override
            public void onNodeChanged(Node node) {
                captureFrame(getCurrentCodeLine(), "com.example.treevisualize.Node Changed");
            }

            @Override
            public void onStructureChanged() {
                captureFrame(getCurrentCodeLine(), "Structure Changed");
            }

            @Override
            public void onError(String message) {
                captureFrame(-1, "Error: " + message);
            }
        };
        tree.addObserver(recorder);
    }

    private void finishRecording() {
        tree.removeObserver(recorder);
        if (!animationFrames.isEmpty()) {
            restoreSnapshot(animationFrames.get(0));
        }
        play();
    }

    private void captureFrame(int lineIndex, String msg) {
        TreeSnapShot snapshot = new TreeSnapShot(tree.getRoot(), lineIndex, msg);
        animationFrames.add(snapshot);
    }
    private int getCurrentCodeLine() {
        return 0;
    }

    public void startTraversal(String type) {
        tree.resetTreeStatus();
        prepareRecording();
        TraversalStrategy strategy = null;

        switch (type) {
            case "In-Order (LNR)":
                strategy = new InOrderTraversal();
                break;
            case "Pre-Order (NLR)":
                strategy = new PreOrderTraversal();
                break;
            case "Post-Order (LRN)":
                strategy = new PostOrderTraversal();
                break;
            case "BFS (Level Order)":
                strategy = new BFSTraversal();
                break;
            default:
                System.out.println("Not Found " + type);
                finishRecording(); // Kết thúc sớm để tránh lỗi
                return;
        }
        if (strategy != null) {
            tree.traverse(strategy);
        }
        finishRecording();
    }

    public void startInsert(int parentValue, int childValue) {
        if (tree instanceof GeneralTree) {
            GeneralTree generalTree = (GeneralTree) tree;
            generalTree.insert(parentValue, childValue);
            visualizer.render();

        } else {
            System.err.println("ERROR: Current tree is not General Tree!");
        }
    }
}
