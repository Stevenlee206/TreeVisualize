package com.example.treevisualize.Visualizer.Events;

import com.example.treevisualize.Visualizer.AlgorithmEvent;

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