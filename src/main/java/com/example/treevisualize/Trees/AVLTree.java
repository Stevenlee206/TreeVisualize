package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.AVLTreeNode;
import com.example.treevisualize.Node.BinaryTreeNode;

// [QUAN TRỌNG] Import các bộ sự kiện (Events)
import com.example.treevisualize.Visualizer.Events.StandardEvent;
import com.example.treevisualize.Visualizer.Events.AVLEvent;

public class AVLTree extends BinarySearchTree {

    // Helper methods giữ nguyên logic
    private int height(BinaryTreeNode n) {
        if (n == null) return 0;
        if (n instanceof AVLTreeNode) {
            return ((AVLTreeNode) n).getHeight();
        }
        return 0;
    }

    private int getBalance(BinaryTreeNode n) {
        if (n == null) return 0;
        return height(n.getLeftChild()) - height(n.getRightChild());
    }

    // --- INSERT LOGIC ---

    @Override
    public void insert(int value) {
        // Sự kiện chung: Bắt đầu
        notifyEvent(StandardEvent.START, root);
        root = insertRecursive((AVLTreeNode) this.root, value);
        notifyStructureChanged();
    }

    private AVLTreeNode insertRecursive(AVLTreeNode node, int value) {
        if (node == null) {
            // Sự kiện chung: Tạo mới
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            AVLTreeNode newNode = new AVLTreeNode(value);
            notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
            return newNode;
        }

        if (value < node.getValue()) {
            // Sự kiện chung: So sánh & Đi trái
            notifyEvent(StandardEvent.COMPARE_LESS, node);
            notifyEvent(StandardEvent.GO_LEFT, node);
            node.setLeftChild(insertRecursive((AVLTreeNode) node.getLeftChild(), value));
        } else if (value > node.getValue()) {
            // Sự kiện chung: So sánh & Đi phải
            notifyEvent(StandardEvent.COMPARE_GREATER, node);
            notifyEvent(StandardEvent.GO_RIGHT, node);
            node.setRightChild(insertRecursive((AVLTreeNode) node.getRightChild(), value));
        } else {
            return node; // Duplicate
        }

        // --- BẮT ĐẦU PHẦN AVL SPECIFIC ---

        // 1. Cập nhật chiều cao
        node.setHeight(1 + Math.max(height(node.getLeftChild()), height(node.getRightChild())));
        notifyEvent(AVLEvent.UPDATE_HEIGHT, node);

        // 2. Tính toán cân bằng
        notifyEvent(AVLEvent.CALC_BF, node);

        return rebalance(node);
    }

    private AVLTreeNode rebalance(AVLTreeNode node) {
        int balance = getBalance(node);

        // Trường hợp Left Left
        if (balance > 1 && getBalance(node.getLeftChild()) >= 0) {
            notifyEvent(AVLEvent.CASE_LL, node);
            return rightRotate(node);
        }

        // Trường hợp Right Right
        if (balance < -1 && getBalance(node.getRightChild()) <= 0) {
            notifyEvent(AVLEvent.CASE_RR, node);
            return leftRotate(node);
        }

        // Trường hợp Left Right
        if (balance > 1 && getBalance(node.getLeftChild()) < 0) {
            notifyEvent(AVLEvent.CASE_LR, node);
            // Highlight xoay con trái trước (dùng sự kiện AVL)
            notifyEvent(AVLEvent.ROTATE_LEFT, node.getLeftChild());
            node.setLeftChild(leftRotate((AVLTreeNode) node.getLeftChild()));
            return rightRotate(node);
        }

        // Trường hợp Right Left
        if (balance < -1 && getBalance(node.getRightChild()) > 0) {
            notifyEvent(AVLEvent.CASE_RL, node);
            // Highlight xoay con phải trước
            notifyEvent(AVLEvent.ROTATE_RIGHT, node.getRightChild());
            node.setRightChild(rightRotate((AVLTreeNode) node.getRightChild()));
            return leftRotate(node);
        }

        return node;
    }

    // --- ROTATE LOGIC ---

    private AVLTreeNode rightRotate(AVLTreeNode y) {
        // Báo cáo: Xoay phải
        notifyEvent(AVLEvent.ROTATE_RIGHT, y);

        AVLTreeNode x = (AVLTreeNode) y.getLeftChild();
        AVLTreeNode T2 = (AVLTreeNode) x.getRightChild();

        // Thực hiện xoay
        x.setRightChild(y);
        y.setLeftChild(T2);

        // Cập nhật height
        y.setHeight(Math.max(height(y.getLeftChild()), height(y.getRightChild())) + 1);
        x.setHeight(Math.max(height(x.getLeftChild()), height(x.getRightChild())) + 1);

        // Cập nhật Parent (quan trọng cho Visualizer vẽ dây)
        if (T2 != null) T2.setParent(y);
        x.setParent(y.getParent()); // x kế thừa cha của y
        y.setParent(x);             // y trở thành con của x

        return x;
    }

    private AVLTreeNode leftRotate(AVLTreeNode x) {
        // Báo cáo: Xoay trái
        notifyEvent(AVLEvent.ROTATE_LEFT, x);

        AVLTreeNode y = (AVLTreeNode) x.getRightChild();
        AVLTreeNode T2 = (AVLTreeNode) y.getLeftChild();

        // Thực hiện xoay
        y.setLeftChild(x);
        x.setRightChild(T2);

        // Cập nhật height
        x.setHeight(Math.max(height(x.getLeftChild()), height(x.getRightChild())) + 1);
        y.setHeight(Math.max(height(y.getLeftChild()), height(y.getRightChild())) + 1);

        // Cập nhật Parent
        if (T2 != null) T2.setParent(x);
        y.setParent(x.getParent());
        x.setParent(y);

        return y;
    }

    // --- DELETE LOGIC ---

    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);
        this.root = deleteRecursive((AVLTreeNode) this.root, value);
        notifyStructureChanged();
    }

    private AVLTreeNode deleteRecursive(AVLTreeNode node, int value) {
        if (node == null) return null;

        if (value < node.getValue()) {
            notifyEvent(StandardEvent.COMPARE_LESS, node);
            node.setLeftChild(deleteRecursive((AVLTreeNode) node.getLeftChild(), value));
        } else if (value > node.getValue()) {
            notifyEvent(StandardEvent.COMPARE_GREATER, node);
            node.setRightChild(deleteRecursive((AVLTreeNode) node.getRightChild(), value));
        } else {
            // Tìm thấy node cần xóa
            if ((node.getLeftChild() == null) || (node.getRightChild() == null)) {
                AVLTreeNode temp = (AVLTreeNode) ((node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild());
                if (temp == null) {
                    node = null; // Không có con
                } else {
                    node = temp; // Có 1 con
                }
                notifyEvent(StandardEvent.DELETE_SUCCESS, node);
            } else {
                // Có 2 con: Tìm successor
                int smallestVal = findSmallestValue((BinaryTreeNode) node.getRightChild());
                node.changeValue(smallestVal);
                // Đệ quy xóa successor
                node.setRightChild(deleteRecursive((AVLTreeNode) node.getRightChild(), smallestVal));
            }
        }

        if (node == null) return null;

        // Cập nhật chiều cao & Cân bằng (giống hệt Insert)
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        notifyEvent(AVLEvent.UPDATE_HEIGHT, node);
        notifyEvent(AVLEvent.CALC_BF, node);

        return rebalance(node);
    }
}