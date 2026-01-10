package com.example.treevisualize.View.Visualizer.Events;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;

public enum BSTEvent implements AlgorithmEvent {
    // common
    CHECK_NODE,
    COMPARE_LESS,
    COMPARE_GREATER,
    GO_LEFT,
    GO_RIGHT,
    BST_ERROR,
    // search
    SEARCH_START,
    SEARCH_SUCCESS,
    SEARCH_FAILED,
    COMPARE_EQUAL,
    // insert
    INSERT_START,      // Mới
    INSERT_ROOT_SUCCESS,  // Tương ứng dòng 2
    INSERT_LEFT_SUCCESS,  // Tương ứng dòng 8
    INSERT_RIGHT_SUCCESS,    // Mới
    CHECK_LEFT_NULL,   // Mới
    CHECK_RIGHT_NULL,
    // delete
    DELETE_START,
    DELETE_SUCCESS,
    FOUND_TARGET,
    DELETE_LEAF,
    REPLACE_WITH_CHILD,
    FIND_SUCCESSOR,
    REPLACE_VALUE
}
