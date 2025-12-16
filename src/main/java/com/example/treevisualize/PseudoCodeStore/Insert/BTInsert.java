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
                "2.  if (root == ∅)",
                "3.  	 return root ← newNode",
                "4.  Q ← Queue()",
                "5.  Q.enqueue(root)",
                "6.  while Q ≠ ∅ do",
                "7.      temp ← Q.dequeue()",
                "8.      if (temp.left == ∅)",
                "9.          temp.left ← newNode",
                "10.         return",
                "11.     else Q.enqueue(temp.left)",
                "12.     if (temp.right == ∅)",
                "13.         temp.right ← newNode",
                "14.         return",
                "15.     else Q.enqueue(temp.right)"
        );
    }
}