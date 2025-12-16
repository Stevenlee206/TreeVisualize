package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree extends Tree{
    public BinaryTree() {
        super();
    }

    @Override
    public void insert(int value) {
        BinaryTreeNode newNode = new BinaryTreeNode(value);
        if (root == null) {
            root = newNode;
            notifyStructureChanged();
            return;
        }
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add((BinaryTreeNode) root);

        while (!queue.isEmpty()) {
            BinaryTreeNode current = queue.poll();
            if (current.getLeftChild() == null) {
                current.setLeftChild(newNode);
                notifyStructureChanged();
                return;
            } else {
                queue.add(current.getLeftChild());
            }
            if (current.getRightChild() == null) {
                current.setRightChild(newNode);
                notifyStructureChanged();
                return;
            } else {
                queue.add(current.getRightChild());
            }
        }
    }

    @Override
    public void delete(int value) {
        if (root == null) return;
        BinaryTreeNode targetNode = (BinaryTreeNode) search(value);

        if (targetNode == null) {
            notifyError("Không tìm thấy giá trị " + value + " để xóa.");
            return;
        }

        if (targetNode == root) {
            clear();
            notifyStructureChanged();
            return;
        }
        BinaryTreeNode parent = (BinaryTreeNode) targetNode.getParent();

        if (parent != null) {
            try {
                parent.removeChild(targetNode);
                notifyStructureChanged();
            } catch (Exception e) {
                notifyError("Lỗi khi xóa: " + e.getMessage());
            }
        } else {
            notifyError("Lỗi cấu trúc: com.example.treevisualize.Node " + value + " không có cha (không phải Root).");
        }
    }

    @Override
    public Node search(int value) {
        if (root == null) return null;
        return searchRecursive((BinaryTreeNode) root, value);
    }

    private BinaryTreeNode searchRecursive(BinaryTreeNode node, int value) {
        if (node == null) return null;

        if (node.getValue() == value) {
            return node;
        }
        BinaryTreeNode leftResult = searchRecursive(node.getLeftChild(), value);
        if (leftResult != null) {
            return leftResult;
        }
        return searchRecursive(node.getRightChild(), value);
    }

    public void insert(int parentVal, int childVal) {
        BinaryTreeNode parent = (BinaryTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Không tìm thấy node cha có giá trị: " + parentVal);
            return;
        }
        if (parent.getLeftChild() == null) {
            parent.setLeftChild(new BinaryTreeNode(childVal));
            notifyStructureChanged();
        }
        else if (parent.getRightChild() == null) {
            parent.setRightChild(new BinaryTreeNode(childVal));
            notifyStructureChanged();
        }
        else {
            notifyError("Node " + parentVal + " đã đủ 2 con. Không thể chèn thêm!");
        }
    }
}
