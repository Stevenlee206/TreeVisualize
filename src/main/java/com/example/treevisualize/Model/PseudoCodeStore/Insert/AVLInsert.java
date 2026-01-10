package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.AVLEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class AVLInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "AVL - Insert (Self-Balancing)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  newNode ← Node(val)",               // Index 0
                "2.  root = insertRecursive(root, val)", // Index 1: START
                "3.  function insertRecursive(node, val):", // Index 2
                "4.      if (node == ∅) return newNode",  // Index 3: CHECK_ROOT_EMPTY
                "5.      node = BST_Insert(node, val)",   // Index 4: COMPARE/GO
                "6.      // --- Rebalancing ---",        // Index 5
                "7.      node.height = updateHeight()",   // Index 6: UPDATE_HEIGHT
                "8.      bf = getBalance(node)",          // Index 7: CALC_BF
                "9.      if (bf > 1 && val < left.val):", // Index 8
                "10.         return RightRotate(node)",   // Index 9: CASE_LL / ROTATE_RIGHT
                "11.     if (bf < -1 && val > right.val):",// Index 10
                "12.         return LeftRotate(node)",    // Index 11: CASE_RR / ROTATE_LEFT
                "13.     if (bf > 1 && val > left.val):", // Index 12
                "14.         return LeftRightRotate(node)",// Index 13: CASE_LR
                "15.     if (bf < -1 && val < right.val):",// Index 14
                "16.         return RightLeftRotate(node)",// Index 15: CASE_RL
                "17.     return node"                     // Index 16
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START -> 1;
                case CHECK_ROOT_EMPTY, INSERT_SUCCESS -> 3;
                case COMPARE_LESS, COMPARE_GREATER, GO_LEFT, GO_RIGHT -> 4;
                default -> -1; // FINISHED trả về -1 để thanh highlight đứng yên tại chỗ
            };
        }

        if (event instanceof AVLEvent ae) {
            return switch (ae) {
                case UPDATE_HEIGHT -> 6;
                case CALC_BF -> 7;
                case CASE_LL, ROTATE_RIGHT -> 9; 
                case CASE_RR, ROTATE_LEFT -> 11;
                case CASE_LR -> 13;
                case CASE_RL -> 15;
                default -> -1;
            };
        }
        return -1;
    }
}