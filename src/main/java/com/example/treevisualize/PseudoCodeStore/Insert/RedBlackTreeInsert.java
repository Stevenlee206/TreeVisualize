package com.example.treevisualize.PseudoCodeStore.Insert;
import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class RedBlackTreeInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "RB-Insert(T, z)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  y ← ∅",
                "2.  x ← T.root",
                "3.  while x ≠ ∅ do",
                "4.      y ← x",
                "5.      if z.key < x.key then x ← x.left",
                "6.      else x ← x.right",
                "7.  z.p ← y",
                "8.  if y == ∅ then T.root ← z",
                "9.  else if z.key < y.key then y.left ← z",
                "10. else y.right ← z",
                "11. z.left ← ∅, z.right ← ∅",
                "12. z.color ← RED",
                "13. RB-Insert-Fixup(T, z)"
        );
    }
}