package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class AVLInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "AVL-Insert(node, value)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1.  if (node == ∅) return new Node(val)",
                "2.  if (val < node.val) node.left ← Insert(node.left, val)",
                "3.  else if (val > node.val) node.right ← Insert(node.right, val)",
                "4.  else return node // No duplicates",
                "5.  node.height ← 1 + max(H(node.left), H(node.right))",
                "6.  bf ← H(node.left) - H(node.right)",
                "7.  // Left-Left Case",
                "8.  if (bf > 1 && val < node.left.val) return RightRotate(node)",
                "9.  // Right-Right Case",
                "10. if (bf < -1 && val > node.right.val) return LeftRotate(node)",
                "11. // Left-Right Case",
                "12. if (bf > 1 && val > node.left.val)",
                "13.     node.left ← LeftRotate(node.left)",
                "14.     return RightRotate(node)",
                "15. // Right-Left Case",
                "16. if (bf < -1 && val < node.right.val)",
                "17.     node.right ← RightRotate(node.right)",
                "18.     return LeftRotate(node)",
                "19. return node"
        );
    }
}