package com.example.treevisualize.Model.PseudoCodeStore.Insert;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.RBTEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class RedBlackTreeInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "RB-Insert(T, z)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  y ← ∅",
                "2.  x ← T.root",
                "3.  while x ≠ ∅ do",
                "4.      y ← x",
                "5.      if (z.key < x.key) x ← x.left",
                "6.      else x ← x.right",
                "7.  z.parent ← y",
                "8.  if (y == ∅) T.root ← z",
                "9.  else if (z.key < y.key) y.left ← z",
                "10. else y.right ← z",
                "11. z.left ← ∅, z.right ← ∅",
                "12. z.color ← RED",
                "13. RB-Insert-Fixup(T, z)"
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case START -> 1;
                case COMPARE_LESS -> 5;
                case GO_LEFT -> 5;
                case COMPARE_GREATER -> 6;
                case GO_RIGHT -> 6;
                case INSERT_SUCCESS -> 10;
                default -> -1;
            };
        }
        if (event instanceof RBTEvent re) {
            return switch (re) {
                case PAINT_RED -> 11;
                case FIXUP_START -> 12;
                case CASE_1, CASE_2, CASE_3, ROTATE_LEFT, ROTATE_RIGHT -> 12;
                default -> -1;
            };
        }
        return -1;
    }
}