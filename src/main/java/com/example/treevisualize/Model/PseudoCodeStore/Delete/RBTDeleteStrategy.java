package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.RBTEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class RBTDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() { 
        return "Red-Black Tree - Delete & fixDelete"; 
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  function delete(value):",                         // Index 0
                "2.      z ← search(value)",                           // Index 1
                "3.      if (z.left == ∅ or z.right == ∅) y ← z",      // Index 2
                "4.      else y ← successor(z)",                       // Index 3
                "5.      if (y.left ≠ ∅) x ← y.left",                  // Index 4
                "6.      else x ← y.right",                            // Index 5
                "7.      if (y.parent == ∅) root ← x",                 // Index 6
                "8.      else y.parent.child ← x",                     // Index 7
                "9.      if (y ≠ z) z.value ← y.value",                // Index 8
                "10.     if (y.color == BLACK) fixDelete(x, y.parent)",// Index 9
                "11. ",                                                // Index 10
                "12. function fixDelete(x, p):",                       // Index 11
                "13.     while (x ≠ root and x.color == BLACK):",      // Index 12
                "14.         w ← sibling(x)",                          // Index 13
                "15.         if (w.color == RED) Case_2_Rotate",       // Index 14
                "16.         if (w.left & w.right are BLACK) Case_1",  // Index 15
                "17.         else Case_3_Rotate_Recolor",              // Index 16
                "18.     x.color ← BLACK"                              // Index 17
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case DELETE_START -> 1;     // Highlight dòng search(value)
                case DELETE_SUCCESS -> 8;   // Highlight dòng cập nhật giá trị (hoàn tất cấu trúc)
                default -> -1;
            };
        }

        if (event instanceof RBTEvent re) {
            return switch (re) {
                case FIXUP_START -> 12;     // Highlight dòng while trong fixDelete
                case CASE_2 -> 14;          // Sibling đỏ
                case CASE_1 -> 15;          // Sibling & Cháu đen
                case CASE_3 -> 16;          // Các trường hợp xoay còn lại
                default -> -1;
            };
        }
        return -1;
    }
}