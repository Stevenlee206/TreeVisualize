package com.example.treevisualize.View.Visualizer.Events;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;

public enum ScapegoatEvent implements AlgorithmEvent {
    UPDATE_SIZE,        // Cập nhật kích thước các node tổ tiên
    CHECK_DEPTH,        // Kiểm tra chiều cao (log_1/alpha)
    FIND_SCAPEGOAT,     // Tìm node "tội đồ" (Scapegoat)
    REBUILD_START,      // Bắt đầu xây lại cây (Rebuild)
    CHECK_WEIGHT        // Kiểm tra trọng số khi xóa (n < alpha * maxSize)
}
