package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Visualizer.Events.StandardEvent; // Import

public class BinarySearchTree extends Tree { // Kế thừa AbstractTree thay vì Tree

    public BinarySearchTree() {
        super();
    }

    // --- SEARCH ---
    @Override
    public Node search(int value) {
        return searchRecursive((BinaryTreeNode) root, value);
    }

    private BinaryTreeNode searchRecursive(BinaryTreeNode current, int value) {
        if (current == null || current.getValue() == value) {
            return current;
        }
        if (value < current.getValue()) {
            notifyEvent(StandardEvent.COMPARE_LESS, current); // Highlight so sánh
            notifyEvent(StandardEvent.GO_LEFT, current);      // Highlight đi trái
            return searchRecursive(current.getLeftChild(), value);
        } else {
            notifyEvent(StandardEvent.COMPARE_GREATER, current);
            notifyEvent(StandardEvent.GO_RIGHT, current);
            return searchRecursive(current.getRightChild(), value);
        }
    }

    // --- INSERT ---
    @Override
    public void insert(int value) {
        notifyEvent(StandardEvent.START, root); // Bắt đầu
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            root = new BinaryTreeNode(value);
            notifyEvent(StandardEvent.INSERT_SUCCESS, root);
            notifyStructureChanged();
            return;
        }
        if (search(value) != null) {
            notifyError("The value " + value + " already exist!");
            return;
        }
        insertRecursive((BinaryTreeNode) root, value);
        notifyStructureChanged();
    }

    private void insertRecursive(BinaryTreeNode current, int value) {
        // [Logic cũ] Vòng lặp tìm vị trí
        if (value < current.getValue()) {
            notifyEvent(StandardEvent.COMPARE_LESS, current);
            if (current.getLeftChild() == null) {
                notifyEvent(StandardEvent.FOUND_INSERT_POS, current);
                current.setLeftChild(new BinaryTreeNode(value));
                notifyEvent(StandardEvent.INSERT_SUCCESS, current.getLeftChild());
            } else {
                notifyEvent(StandardEvent.GO_LEFT, current);
                insertRecursive(current.getLeftChild(), value);
            }
        } else if (value > current.getValue()) {
            notifyEvent(StandardEvent.COMPARE_GREATER, current);
            if (current.getRightChild() == null) {
                notifyEvent(StandardEvent.FOUND_INSERT_POS, current);
                current.setRightChild(new BinaryTreeNode(value));
                notifyEvent(StandardEvent.INSERT_SUCCESS, current.getRightChild());
            } else {
                notifyEvent(StandardEvent.GO_RIGHT, current);
                insertRecursive(current.getRightChild(), value);
            }
        }
    }

    // --- DELETE ---
    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);
        if (root == null) return;
        // Search đã có notify bên trong
        if (search(value) == null) {
            notifyError("Cannot delete: value " + value + " not found.");
            return;
        }
        root = deleteRecursive((BinaryTreeNode) root, value);
        notifyStructureChanged();
    }

    private BinaryTreeNode deleteRecursive(BinaryTreeNode current, int value) {
        if (current == null) return null;

        if (value < current.getValue()) {
            notifyEvent(StandardEvent.COMPARE_LESS, current);
            BinaryTreeNode newLeft = deleteRecursive(current.getLeftChild(), value);
            current.setLeftChild(newLeft);
            return current;
        } else if (value > current.getValue()) {
            notifyEvent(StandardEvent.COMPARE_GREATER, current);
            BinaryTreeNode newRight = deleteRecursive(current.getRightChild(), value);
            current.setRightChild(newRight);
            return current;
        } else {
            // Tìm thấy node cần xóa
            notifyEvent(StandardEvent.DELETE_SUCCESS, current); // Hoặc tạo event FOUND_DELETE_TARGET

            if (current.getLeftChild() == null && current.getRightChild() == null) {
                return null;
            }
            if (current.getLeftChild() == null) return current.getRightChild();
            if (current.getRightChild() == null) return current.getLeftChild();

            int smallestValue = findSmallestValue(current.getRightChild());
            current.changeValue(smallestValue);
            BinaryTreeNode newRight = deleteRecursive(current.getRightChild(), smallestValue);
            current.setRightChild(newRight);
            return current;
        }
    }

    // (Giữ nguyên findSmallestValue)
    protected int findSmallestValue(BinaryTreeNode root) {
        int min = root.getValue();
        while (root.getLeftChild() != null) {
            min = root.getLeftChild().getValue();
            root = root.getLeftChild();
        }
        return min;
    }
}