package com.example.treevisualize.Model.PseudoCodeStore.Delete;

import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Visualizer.AlgorithmEvent;
import com.example.treevisualize.View.Visualizer.Events.RBTEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import java.util.Arrays;
import java.util.List;

public class RBTDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() { return "RB-Delete(T, z)"; }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  z ← search(value)",
                "2.  if (z == ∅) return ",
                "3.  RedBlackTreeNode x, y",
                "4.  if (z.left == ∅ || z.right == ∅) y ← z",
                "5.  else y ← z.successor",
                "6.  if (y.left ≠ ∅) x ← y.left",
                "7.  else x ← y.right",
                "8.  if (x ≠ ∅) x.parent ← y.parent",
                "9.  if (y.parent == ∅)",
                "10.     root ← x",
                "11. else if (y = y.parent.left)",
                "12.     y.parent.left ← x",
                "13. else",
                "14.     y.parent.right ← x",
                "15. if (y ≠ z) z.value ← y.value",
                "16. if (y.color = BLACK)",
                "17.     deleteFixUp(x, y.parent)"
        );
    }

    @Override
    public int getLineIndex(AlgorithmEvent event) {
        if (event instanceof StandardEvent se) {
            return switch (se) {
                case DELETE_START -> 0;
                case DELETE_SUCCESS -> 14;
                default -> -1;
            };
        }
        if (event instanceof RBTEvent re) {
            if (re == RBTEvent.FIXUP_START || re == RBTEvent.CASE_1 ||
                    re == RBTEvent.ROTATE_LEFT || re == RBTEvent.ROTATE_RIGHT) {
                return 16;
            }
        }
        return -1;
    }
}