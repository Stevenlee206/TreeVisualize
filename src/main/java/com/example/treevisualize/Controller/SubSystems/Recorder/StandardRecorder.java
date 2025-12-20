package com.example.treevisualize.Controller.SubSystems.Recorder;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Trees.Tree;
import com.example.treevisualize.Visualizer.TreeObserver;
import com.example.treevisualize.Visualizer.TreeSnapShot;

import java.util.ArrayList;
import java.util.List;

public class StandardRecorder implements RecorderStrategy, TreeObserver {
    private final Tree tree;
    private final List<TreeSnapShot> buffer = new ArrayList<>();
    private boolean isRecording = false;

    private int currentLineIndex = -1;

    public StandardRecorder(Tree tree) {

        this.tree = tree;

    }

    @Override
    public void startRecording() {
        buffer.clear();
        isRecording = true;

        // Đăng ký làm Observer để lắng nghe cây
        tree.addObserver(this);

        // Chụp frame đầu tiên
        capture(-1, "Start Transaction");
    }

    @Override
    public List<TreeSnapShot> stopRecording() {
        isRecording = false;
        tree.removeObserver(this); // Ngừng lắng nghe để tiết kiệm tài nguyên
        return new ArrayList<>(buffer); // Trả về bản sao để an toàn
    }

    // --- Logic Observer nội bộ ---

    private void capture(int line, String msg) {
        // Deep copy root để lưu trạng thái tại thời điểm này
        buffer.add(new TreeSnapShot(tree.getRoot(),msg,line));
    }

    @Override
    public void onNodeChanged(Node node) {
        // Khi node đổi màu/giá trị, ta chụp lại nhưng vẫn giữ dòng code hiện tại đang sáng
        if (isRecording) {
            capture(currentLineIndex, "Node Changed: " + node.getValue());
        }
    }

    @Override
    public void onStructureChanged() {
        // Khi cây xoay hoặc thêm/bớt node
        if (isRecording) {
            capture(currentLineIndex, "Structure Changed");
        }
    }

    @Override
    public void onError(String message) {
        if (isRecording) {
            capture(-1, "Error: " + message);
        }
    }
    @Override
    public void onPseudoStep(int lineIndex) {
        if (isRecording) {
            this.currentLineIndex = lineIndex; // Cập nhật dòng code mới
            capture(lineIndex, "Step " + lineIndex);
        }
    }
}