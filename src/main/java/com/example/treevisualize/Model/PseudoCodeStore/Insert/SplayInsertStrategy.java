package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.SplayEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class SplayInsertStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "Splay Tree - Insert & Splay"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  function insert(val):",                         // Index 0
                "2.      node ← BST_Insert(val)",                    // Index 1
                "3.      splay(node)",                               // Index 2
                "4.  ",                                              // Index 3
                "5.  function splay(node):",                         // Index 4
                "6.      while (node.p ≠ ∅):",                       // Index 5
                "7.          if (node.gp == ∅):",                    // Index 6
                "8.              if (node == p.left) rotateRight(p)",// Index 7
                "9.              else rotateLeft(p)",                // Index 8
                "10.         else if (node == p.left and p == gp.left):", // Index 9
                "11.             rotateRight(gp), rotateRight(p)",   // Index 10
                "12.         else if (node == p.right and p == gp.right):", // Index 11
                "13.             rotateLeft(gp), rotateLeft(p)",     // Index 12
                "14.         else:",                                 // Index 13
                "15.             if (node == p.right) rotateLeft(p), rotateRight(gp)", // Index 14
                "16.             else rotateRight(p), rotateLeft(gp)", // Index 15
                "17. ",                                              // Index 16
                "18. function rotateRight(p):",                      // Index 17
                "19.     x ← p.left, T2 ← x.right, gp ← p.p",        // Index 18
                "20.     x.right ← p, p.left ← T2",                  // Index 19
                "21.     updateParent(gp, p, x)",                    // Index 20
                "22. ",                                              // Index 21
                "23. function rotateLeft(p):",                       // Index 22
                "24.     x ← p.right, T2 ← x.left, gp ← p.p",        // Index 23
                "25.     x.left ← p, p.right ← T2",                  // Index 24
                "26.     updateParent(gp, p, x)",                    // Index 25
                "27. ",                                              // Index 26
                "28. function updateParent(gp, old, new):",          // Index 27
                "29.     if (gp == ∅) root ← new",                   // Index 28
                "30.     else if (gp.left == old) gp.left ← new",    // Index 29
                "31.     else gp.right ← new"                        // Index 30
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START -> 1;
                case INSERT_SUCCESS -> 2;
                default -> -1;
            };
        }
        if (event instanceof SplayEvent e) {
            return switch (e) {
                case SPLAY_START -> 4;
                case CASE_ZIG -> 6;
                case CASE_ZIG_ZIG -> 9;  
                case CASE_ZIG_ZAG -> 13; 
                case ROTATE_RIGHT -> 18; // Highlight logic trong hàm rotateRight
                case ROTATE_LEFT -> 23;  // Highlight logic trong hàm rotateLeft
                default -> -1;
            };
        }
        return -1;
    }
}