package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class AVLDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "AVL-Delete(root, val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  node ← BST-Delete(node, val)",
                "2.  if (node == ∅) return node",
                "3.  node.height ← 1 + max(H(node.left), H(node.right))",
                "4.  bf ← H(node.left) - H(node.right)",
                "5.  // Left Heavy",
                "6.  if (bf > 1 && getBalance(node.left) ≥ 0)",
                "7.      return RightRotate(node)",
                "8.  if (bf > 1 && getBalance(node.left) < 0)",
                "9.      node.left ← LeftRotate(node.left)",
                "10.     return RightRotate(node)",
                "11. // Right Heavy",
                "12. if (bf < -1 && getBalance(node.right) ≤ 0)",
                "13.     return LeftRotate(node)",
                "14. if (bf < -1 && getBalance(node.right) > 0)",
                "15.     node.right ← RightRotate(node.right)",
                "16.     return LeftRotate(node)",
                "17. return node"
        );
    }
}