package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.AVLEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;

import java.util.Arrays;
import java.util.List;

public class AVLDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "AVL-Delete(root, val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  node ← BST-Delete(node, val)",                       // Index 0
                "2.  if (node == ∅) return node",                         // Index 1
                "3.  node.height ← 1 + max(H(node.left), H(node.right))", // Index 2
                "4.  bf ← H(node.left) - H(node.right)",                  // Index 3
                "5.  // Left Heavy",                                      // Index 4
                "6.  if (bf > 1 && getBalance(node.left) ≥ 0)",           // Index 5 (CASE_LL)
                "7.      return RightRotate(node)",                       // Index 6
                "8.  if (bf > 1 && getBalance(node.left) < 0)",           // Index 7 (CASE_LR)
                "9.      node.left ← LeftRotate(node.left)",              // Index 8
                "10.     return RightRotate(node)",                       // Index 9 (Map ROTATE_RIGHT)
                "11. // Right Heavy",                                     // Index 10
                "12. if (bf < -1 && getBalance(node.right) ≤ 0)",         // Index 11 (CASE_RR)
                "13.     return LeftRotate(node)",                        // Index 12
                "14. if (bf < -1 && getBalance(node.right) > 0)",         // Index 13 (CASE_RL)
                "15.     node.right ← RightRotate(node.right)",           // Index 14
                "16.     return LeftRotate(node)",                        // Index 15 (Map ROTATE_LEFT)
                "17. return node"                                         // Index 16
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        return switch (event) {

            // 1. Nhóm sự kiện chuẩn
            case StandardEvent se -> switch (se) {
                case DELETE_START -> 0;
                case DELETE_SUCCESS -> 1;
                // Highlight quá trình tìm kiếm node cần xóa ở dòng 0 (gọi BST-Delete)
                case COMPARE_LESS, GO_LEFT, COMPARE_GREATER, GO_RIGHT -> 0;
                default -> -1;
            };

            // 2. Nhóm sự kiện AVL
            case AVLEvent ae -> switch (ae) {
                case UPDATE_HEIGHT -> 2;
                case CALC_BF -> 3;
                case CASE_LL -> 5;
                case CASE_LR -> 7;
                case CASE_RR -> 11;
                case CASE_RL -> 13;
                case ROTATE_RIGHT -> 9;  // Highlight dòng return RightRotate
                case ROTATE_LEFT -> 15; // Highlight dòng return LeftRotate
                default -> -1;
            };

            default -> -1;
        };
    }
}