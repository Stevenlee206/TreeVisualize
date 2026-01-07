package com.example.treevisualize.View.Visualizer.Events;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;

public enum StandardEvent implements AlgorithmEvent {
    START,
    CHECK_ROOT_EMPTY,
    COMPARE_LESS,
    GO_LEFT,
    COMPARE_GREATER,
    GO_RIGHT,
    FOUND_INSERT_POS,
    INSERT_SUCCESS,
    DELETE_START,
    DELETE_SUCCESS,
    SEARCH_CHECK,      
    SEARCH_RECURSE,    
    SEARCH_FOUND,
    SEARCH_START,
    SEARCH_END;
}