package com.example.treevisualize.Visualizer.Events;

import com.example.treevisualize.Visualizer.AlgorithmEvent;

public enum TraversalEvent implements AlgorithmEvent {
    // Sự kiện quản lý luồng
    START,              // Bắt đầu
    FINISHED,           // Kết thúc

    // Sự kiện quan trọng cần Highlight
    TAKE_FROM_DS,       // Lấy ra khỏi Queue (BFS)
    VISIT               // Thêm vào Result (Quan trọng nhất)
}
