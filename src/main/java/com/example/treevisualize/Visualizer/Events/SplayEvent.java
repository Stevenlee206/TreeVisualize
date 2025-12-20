package com.example.treevisualize.Visualizer.Events;

import com.example.treevisualize.Visualizer.AlgorithmEvent;

public enum SplayEvent implements AlgorithmEvent {
    SPLAY_START,    // Bắt đầu quá trình Splay
    CASE_ZIG,       // Trường hợp cha là Root (Zig/Zag)
    CASE_ZIG_ZIG,   // Trường hợp cùng phía (Zig-Zig / Zag-Zag)
    CASE_ZIG_ZAG,   // Trường hợp gấp khúc (Zig-Zag / Zag-Zig)

    ROTATE_LEFT,
    ROTATE_RIGHT
}