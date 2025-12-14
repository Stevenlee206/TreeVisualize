package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Node.Node;
import java.util.ArrayList;
import java.util.List;

public class InOrderTraversal implements TraversalStrategy {

    @Override
    public List<Node> traverse(Node root) {
        List<Node> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(Node node, List<Node> result) {
        if (node == null) {
            return;
        }

        // --- TRƯỜNG HỢP 1: CÂY NHỊ PHÂN (Binary/BST/RedBlack) ---
        if (node instanceof BinaryTreeNode) {
            BinaryTreeNode binaryNode = (BinaryTreeNode) node;

            // Trái -> Gốc -> Phải
            inOrderRecursive(binaryNode.getLeftChild(), result);
            result.add(binaryNode);
            inOrderRecursive(binaryNode.getRightChild(), result);
        }

        // --- TRƯỜNG HỢP 2: CÂY TỔNG QUÁT (GeneralTreeNode) ---
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode gNode = (GeneralTreeNode) node;
            GeneralTreeNode firstChild = gNode.getLeftMostChild();

            // Bước A: Duyệt đệ quy con đầu tiên (nếu có)
            // Tương đương với việc đi sang nhánh Trái
            if (firstChild != null) {
                inOrderRecursive(firstChild, result);
            }

            // Bước B: Thăm nút GỐC (Cha)
            // Tương đương nút Gốc nằm giữa
            result.add(gNode);

            // Bước C: Duyệt đệ quy các con còn lại (nếu có)
            // Tương đương với việc đi sang nhánh Phải
            if (firstChild != null) {
                GeneralTreeNode currentSibling = firstChild.getRightSibling();

                // Duyệt hết danh sách anh em của con cả
                while (currentSibling != null) {
                    inOrderRecursive(currentSibling, result);
                    currentSibling = currentSibling.getRightSibling();
                }
            }
        }

        // --- TRƯỜNG HỢP KHÁC (Fallback) ---
        else {
            result.add(node);
        }
    }
}