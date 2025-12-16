package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class BSTInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "BST-Insert(root, val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if root == ∅ then",
                "2.      return new Node(val)",
                "3.  current ← root",
                "4.  while true do",
                "5.      if val < current.val then",
                "6.          if current.left == ∅ then",
                "7.              current.left ← new Node(val)",
                "8.              break",
                "9.          else current ← current.left",
                "10.     else",
                "11.         if current.right == ∅ then",
                "12.             current.right ← new Node(val)",
                "13.             break",
                "14.         else current ← current.right"
        );
    }
}