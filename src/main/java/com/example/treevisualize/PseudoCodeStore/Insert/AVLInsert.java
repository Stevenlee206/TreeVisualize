package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Visualizer.AlgorithmEvent;
import com.example.treevisualize.Visualizer.Events.AVLEvent;
import com.example.treevisualize.Visualizer.Events.StandardEvent;

import java.util.Arrays;
import java.util.List;

public class AVLInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "AVL-Insert(node, value)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if (node == ∅) return new Node(val)",                // Index 0
                "2.  if (val < node.val) node.left ← Insert(node.left, val)", // Index 1
                "3.  else if (val > node.val) node.right ← Insert(node.right, val)", // Index 2
                "4.  else return node // No duplicates",                  // Index 3
                "5.  node.height ← 1 + max(H(node.left), H(node.right))", // Index 4
                "6.  bf ← H(node.left) - H(node.right)",                  // Index 5
                "7.  // Left-Left Case",                                  // Index 6
                "8.  if (bf > 1 && val < node.left.val) return RightRotate(node)", // Index 7 (CASE_LL)
                "9.  // Right-Right Case",                                // Index 8
                "10. if (bf < -1 && val > node.right.val) return LeftRotate(node)", // Index 9 (CASE_RR)
                "11. // Left-Right Case",                                 // Index 10
                "12. if (bf > 1 && val > node.left.val)",                 // Index 11 (CASE_LR)
                "13.     node.left ← LeftRotate(node.left)",              // Index 12
                "14.     return RightRotate(node)",                       // Index 13 (Map ROTATE_RIGHT vào đây)
                "15. // Right-Left Case",                                 // Index 14
                "16. if (bf < -1 && val < node.right.val)",               // Index 15 (CASE_RL)
                "17.     node.right ← RightRotate(node.right)",           // Index 16
                "18.     return LeftRotate(node)",                        // Index 17 (Map ROTATE_LEFT vào đây)
                "19. return node"                                         // Index 18
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        // Sử dụng Switch Expression với Pattern Matching (Java 17+)
        return switch (event) {

            // 1. Nhóm sự kiện chuẩn (StandardEvent)
            case StandardEvent se -> switch (se) {
                case CHECK_ROOT_EMPTY, INSERT_SUCCESS -> 0;
                case COMPARE_LESS, GO_LEFT -> 1;
                case COMPARE_GREATER, GO_RIGHT -> 2;
                default -> -1;
            };

            // 2. Nhóm sự kiện AVL (AVLEvent)
            case AVLEvent ae -> switch (ae) {
                case UPDATE_HEIGHT -> 4;
                case CALC_BF -> 5;
                case CASE_LL -> 7;
                case CASE_RR -> 9;
                case CASE_LR -> 11;
                case CASE_RL -> 15;
                case ROTATE_RIGHT -> 13;
                case ROTATE_LEFT -> 17;
                default -> -1;
            };

            // Các loại event khác (nếu có)
            default -> -1;
        };
    }
}