package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class BSTDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "BST-Delete(T, z)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if z.left == ∅",
                "2.      Transplant(T, z, z.right)",
                "3.  else if z.right == ∅",
                "4.      Transplant(T, z, z.left)",
                "5.  else",
                "6.      y ← Tree-Minimum(z.right)",
                "7.      if y.p ≠ z",
                "8.          Transplant(T, y, y.right)",
                "9.          y.right ← z.right",
                "10.         y.right.p ← y",
                "11.     Transplant(T, z, y)",
                "12.     y.left ← z.left",
                "13.     y.left.p ← y"
        );
    }
}