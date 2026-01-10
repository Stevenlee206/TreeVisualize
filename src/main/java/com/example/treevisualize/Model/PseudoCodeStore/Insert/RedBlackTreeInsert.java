package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.RBTEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class RedBlackTreeInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() { 
        return "Red-Black Tree - Insert Logic"; 
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  function insert(value):",                                 // Index 0
                "2.      z ← search(value)",                                   // Index 1
                "3.      if (z ≠ ∅) return error",                             // Index 2
                "4.      BST_Insert_Structural(k)",                            // Index 3
                "5.      fixInsert(k)",                                        // Index 4
                "6.  ",                                                        // Index 5
                "7.  function BST_Insert_Structural(k):",                      // Index 6
                "8.      find_pos(k), p.child ← k, color(k) ← RED",            // Index 7
                "9.  ",                                                        // Index 8
                "10. function fixInsert(k):",                                  // Index 9
                "11.     while (k ≠ root and color(p) == RED):",               // Index 10
                "12.         if (p == gp.left) u ← gp.right",                  // Index 11
                "13.         else u ← gp.left",                                // Index 12
                "14.         if (color(u) == RED) // Case_1",                  // Index 13
                "15.             recolor(p, u, gp), k ← gp",                   // Index 14
                "16.         else if (k == inner_child) // Case_2",            // Index 15
                "17.             k ← p, rotate(k)",                            // Index 16
                "18.         else // Case_3",                                  // Index 17
                "19.             recolor(p, gp), rotate(gp)",                  // Index 18
                "20.     root.color ← BLACK"                                   // Index 19
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START -> 1;            // z ← search(value)
                case INSERT_SUCCESS -> 7;   // Nội dung hàm BST_Insert_Structural
                default -> -1;
            };
        }

        if (event instanceof RBTEvent re) {
            return switch (re) {
                case PAINT_RED -> 7;        // Node đỏ xuất hiện
                case FIXUP_START -> 10;     // Dòng while (Index 10)
                case CASE_1 -> 14;          // Thực thi recolor (Dưới dòng if)
                case CASE_2 -> 16;          // Thực thi rotate (Dưới dòng else if)
                case CASE_3 -> 18;          // Thực thi recolor/rotate (Dưới dòng else)
                default -> -1;
            };
        }
        return -1;
    }
}

