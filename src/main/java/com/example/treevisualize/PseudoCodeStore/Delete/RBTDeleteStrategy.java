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
                "1.  y ← z, y-original-color ← y.color",
                "2.  if z.left == ∅",
                "3.      x ← z.right, Transplant(T, z, z.right)",
                "4.  else if z.right == ∅",
                "5.      x ← z.left, Transplant(T, z, z.left)",
                "6.  else",
                "7.      y ← Minimum(z.right)",
                "8.      y-original-color ← y.color",
                "9.      x ← y.right",
                "10.     if y.p == z then x.p ← y",
                "11.     else Transplant(T, y, y.right)",
                "12.     Transplant(T, z, y)",
                "13. if y-original-color == BLACK",
                "14.     RB-Delete-Fixup(T, x)"
        );
    }
}
