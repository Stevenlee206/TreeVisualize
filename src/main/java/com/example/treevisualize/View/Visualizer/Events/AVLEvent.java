package com.example.treevisualize.View.Visualizer.Events;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;

public enum AVLEvent implements AlgorithmEvent {
    UPDATE_HEIGHT,
    CALC_BF,

    // Các trường hợp đặc thù của AVL
    CASE_LL,
    CASE_RR,
    CASE_LR,
    CASE_RL,

    ROTATE_LEFT,
    ROTATE_RIGHT
}