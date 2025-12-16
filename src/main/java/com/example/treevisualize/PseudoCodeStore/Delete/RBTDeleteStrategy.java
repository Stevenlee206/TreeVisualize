package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class RBTDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "RB-Delete(T, z)";
    }

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
                "17. 	 deleteFixUp(x, y.parent)"
        );
    }
}
