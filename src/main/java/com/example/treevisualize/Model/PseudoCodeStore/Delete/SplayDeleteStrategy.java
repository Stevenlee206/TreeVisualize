package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.SplayEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class SplayDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Splay Tree - Delete & Splay"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  function delete(val):",                         // Index 0
                "2.      z ← search(val)",                           // Index 1
                "3.      if (z == ∅) return",                        // Index 2
                "4.      p ← z.parent",                              // Index 3
                "5.      BST_Delete_Structural(z)",                  // Index 4
                "6.      if (p ≠ ∅) splay(p)",                       // Index 5
                "7.      else if (root ≠ ∅) splay(root)",            // Index 6
                "8.  ",                                              // Index 7
                "9.  function BST_Delete_Structural(z):",            // Index 8
                "10.     standard_BST_delete(z)",                    // Index 9
                "11. ",                                              // Index 10
                "12. function splay(node):",                         // Index 11
                "13.     while (node.p ≠ ∅):",                       // Index 12
                "14.         if (node.gp == ∅):",                    // Index 13
                "15.             if (node == p.left) rotateRight(p)",// Index 14
                "16.             else rotateLeft(p)",                // Index 15
                "17.         else if (node == p.left and p == gp.left):", // Index 16
                "18.             rotateRight(gp), rotateRight(p)",   // Index 17
                "19.         else if (node == p.right and p == gp.right):", // Index 18
                "20.             rotateLeft(gp), rotateLeft(p)",     // Index 19
                "21.         else:",                                 // Index 20
                "22.             if (node == p.right) rotateLeft(p), rotateRight(gp)", // Index 21
                "23.             else rotateRight(p), rotateLeft(gp)", // Index 22
                "24. ",                                              // Index 23
                "25. function rotateRight(p):",                      // Index 24
                "26.     x ← p.left, T2 ← x.right, gp ← p.p",        // Index 25
                "27.     x.right ← p, p.left ← T2",                  // Index 26
                "28.     updateParent(gp, p, x)",                    // Index 27
                "29. ",                                              // Index 28
                "30. function rotateLeft(p):",                       // Index 29
                "31.     x ← p.right, T2 ← x.left, gp ← p.p",        // Index 30
                "32.     x.left ← p, p.right ← T2",                  // Index 31
                "33.     updateParent(gp, p, x)",                    // Index 32
                "34. ",                                              // Index 33
                "35. function updateParent(gp, old, new):",          // Index 34
                "36.     if (gp == ∅) root ← new",                   // Index 35
                "37.     else if (gp.left == old) gp.left ← new",    // Index 36
                "38.     else gp.right ← new"                        // Index 37
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case DELETE_START -> 1;     // Highlight bước search node cần xóa
                case DELETE_SUCCESS -> 9;   // Highlight trong hàm BST_Delete_Structural
                default -> -1;
            };
        }
        if (event instanceof SplayEvent e) {
            return switch (e) {
                case SPLAY_START -> 11;     // Bắt đầu hàm splay(p) hoặc splay(root)
                case CASE_ZIG -> 13;        
                case CASE_ZIG_ZIG -> 16;    
                case CASE_ZIG_ZAG -> 20;    
                case ROTATE_RIGHT -> 25;    
                case ROTATE_LEFT -> 30;     
                default -> -1;
            };
        }
        return -1;
    }
}