package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class BTInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "LevelOrder-Insert(root, val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  newNode ← new Node(val)",
                "2.  Q ← Queue()",
                "3.  Q.enqueue(root)",
                "4.  while Q ≠ ∅ do",
                "5.      temp ← Q.dequeue()",
                "6.      if temp.left == ∅ then",
                "7.          temp.left ← newNode",
                "8.          return",
                "9.      else Q.enqueue(temp.left)",
                "10.     if temp.right == ∅ then",
                "11.         temp.right ← newNode",
                "12.         return",
                "13.     else Q.enqueue(temp.right)"
        );
    }
}