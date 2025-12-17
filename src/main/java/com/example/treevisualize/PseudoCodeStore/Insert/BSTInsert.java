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
                "1.  if (root == ∅)",
                "2.      root ← new Node(val)",
                "3.		 return",
                "4.  current ← root",
                "5.  while true do",
                "6.      if (val < current.val)",
                "7.          if (current.left == ∅)",
                "8.              current.left ← new Node(val)",
                "9.              break",
                "10.         else current ← current.left",
                "11.     else",
                "12.         if (current.right == ∅)",
                "13.             current.right ← new Node(val)",
                "14.             break",
                "15.         else current ← current.right"
        );
    }
}