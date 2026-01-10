package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.AVLEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class AVLDeleteStrategy implements PseudoCodeStrategy {
    
    @Override
    public String getTitle() { return "AVL - Delete (Detailed Rebalance)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  target = search(val)",               // Index 0
                "2.  root = deleteRecursive(root, val)", // Index 1: DELETE_START
                "3.  function deleteRecursive(node, val):", // Index 2
                "4.      node = BST_Delete(node, val)",   // Index 3: StandardEvents
                "5.      node.height = updateHeight()",   // Index 4: UPDATE_HEIGHT
                "6.      bf = getBalance(node)",          // Index 5: CALC_BF
                "7.      if (bf > 1 && getBalance(left) >= 0):", // Index 6 (LL Check)
                "8.          return RightRotate(node)",   // Index 7 (LL Action)
                "9.      if (bf < -1 && getBalance(right) <= 0):", // Index 8 (RR Check)
                "10.         return LeftRotate(node)",    // Index 9 (RR Action)
                "11.     if (bf > 1 && getBalance(left) < 0):",  // Index 10 (LR Check)
                "12.         return LeftRightRotate(node)", // Index 11 (LR Action)
                "13.     if (bf < -1 && getBalance(right) > 0):", // Index 12 (RL Check)
                "14.         return RightLeftRotate(node)", // Index 13 (RL Action)
                "15.     return node"                     // Index 14
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case DELETE_START -> 1;
                case COMPARE_LESS, COMPARE_GREATER, DELETE_SUCCESS -> 3;
                // FINISHED trả về -1 để thanh highlight đứng yên tại dòng thực hiện cuối cùng
                default -> -1;
            };
        }

        if (event instanceof AVLEvent ae) {
            return switch (ae) {
                case UPDATE_HEIGHT -> 4;
                case CALC_BF -> 5;
                case CASE_LL -> 6;  case ROTATE_RIGHT -> 7;
                case CASE_RR -> 8;  case ROTATE_LEFT -> 9;
                case CASE_LR -> 10; 
                case CASE_RL -> 12;
                default -> -1;
            };
        }
        return -1;
    }
}