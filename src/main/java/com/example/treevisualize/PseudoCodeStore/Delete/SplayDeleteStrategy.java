package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class SplayDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Splay-Delete(val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  target ← Search(val)",
                "2.  if target == ∅ return error",
                "3.  parent ← target.parent",
                "4.  BST-Delete(target) // Xóa như BST thường",
                "5.  if parent ≠ ∅ then",
                "6.      Splay(parent) // Đưa cha lên làm Gốc",
                "7.  else if root ≠ ∅",
                "8.      Splay(root) // Cân bằng lại cây"
        );
    }
}