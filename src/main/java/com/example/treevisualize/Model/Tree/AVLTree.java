package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.AVLTreeNode;
import com.example.treevisualize.Model.Node.BinaryTreeNode;
import com.example.treevisualize.Model.Node.NodeStatus; // Cần Import thêm cái này
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import com.example.treevisualize.View.Visualizer.Events.AVLEvent;

public class AVLTree extends BinarySearchTree {

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
        notifyEvent(StandardEvent.START, root);
        root = insertRecursive((AVLTreeNode) this.root, value);
        notifyStructureChanged();
    }

    private AVLTreeNode insertRecursive(AVLTreeNode node, int value) {
        if (node == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            return new AVLTreeNode(value);
        }

        // BẮT ĐẦU HIGHLIGHT VÀNG
        node.changeStatus(NodeStatus.ACTIVE);
        notifyNodeChanged(node);

        if (value < node.getValue()) {
            notifyEvent(StandardEvent.COMPARE_LESS, node);
            notifyEvent(StandardEvent.GO_LEFT, node);
            node.setLeftChild(insertRecursive((AVLTreeNode) node.getLeftChild(), value));
        } else if (value > node.getValue()) {
            notifyEvent(StandardEvent.COMPARE_GREATER, node);
            notifyEvent(StandardEvent.GO_RIGHT, node);
            node.setRightChild(insertRecursive((AVLTreeNode) node.getRightChild(), value));
        } else {
            node.changeStatus(NodeStatus.NORMAL); // Trả lại màu cũ nếu trùng
            notifyNodeChanged(node);
            return node;
        }

        // --- AVL SPECIFIC (Cân bằng lại khi đệ quy quay ngược lên) ---

        // Cập nhật chiều cao
        node.setHeight(1 + Math.max(height(node.getLeftChild()), height(node.getRightChild())));
        notifyEvent(AVLEvent.UPDATE_HEIGHT, node);

        // Tính toán cân bằng
        notifyEvent(AVLEvent.CALC_BF, node);

        AVLTreeNode result = rebalance(node);

        // KẾT THÚC HIGHLIGHT (Trả về màu bình thường sau khi đã cân bằng xong ở node này)
        node.changeStatus(NodeStatus.NORMAL);
        notifyNodeChanged(node);

        return result;
    }

    private AVLTreeNode rebalance(AVLTreeNode node) {
        int balance = getBalance(node);

        // Case LL
        if (balance > 1 && getBalance(node.getLeftChild()) >= 0) {
            notifyEvent(AVLEvent.CASE_LL, node);
            return rightRotate(node);
        }

        // Case RR
        if (balance < -1 && getBalance(node.getRightChild()) <= 0) {
            notifyEvent(AVLEvent.CASE_RR, node);
            return leftRotate(node);
        }

        // Case LR
        if (balance > 1 && getBalance(node.getLeftChild()) < 0) {
            notifyEvent(AVLEvent.CASE_LR, node);
            notifyEvent(AVLEvent.ROTATE_LEFT, node.getLeftChild());
            node.setLeftChild(leftRotate((AVLTreeNode) node.getLeftChild()));
            return rightRotate(node);
        }

        // Case RL
        if (balance < -1 && getBalance(node.getRightChild()) > 0) {
            notifyEvent(AVLEvent.CASE_RL, node);
            notifyEvent(AVLEvent.ROTATE_RIGHT, node.getRightChild());
            node.setRightChild(rightRotate((AVLTreeNode) node.getRightChild()));
            return leftRotate(node);
        }

        return node;
    }

    // --- ROTATE LOGIC ---

    private AVLTreeNode rightRotate(AVLTreeNode y) {
        notifyEvent(AVLEvent.ROTATE_RIGHT, y);

        AVLTreeNode x = (AVLTreeNode) y.getLeftChild();
        AVLTreeNode T2 = (AVLTreeNode) x.getRightChild();

        x.setRightChild(y);
        y.setLeftChild(T2);

        y.setHeight(Math.max(height(y.getLeftChild()), height(y.getRightChild())) + 1);
        x.setHeight(Math.max(height(x.getLeftChild()), height(x.getRightChild())) + 1);

        if (T2 != null) T2.setParent(y);
        x.setParent(y.getParent());
        y.setParent(x);

        return x;
    }

    private AVLTreeNode leftRotate(AVLTreeNode x) {
        notifyEvent(AVLEvent.ROTATE_LEFT, x);

        AVLTreeNode y = (AVLTreeNode) x.getRightChild();
        AVLTreeNode T2 = (AVLTreeNode) y.getLeftChild();

        y.setLeftChild(x);
        x.setRightChild(T2);

        x.setHeight(Math.max(height(x.getLeftChild()), height(x.getRightChild())) + 1);
        y.setHeight(Math.max(height(y.getLeftChild()), height(y.getRightChild())) + 1);

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

        // BẮT ĐẦU HIGHLIGHT VÀNG
        node.changeStatus(NodeStatus.ACTIVE);
        notifyNodeChanged(node);

        if (value < node.getValue()) {
            notifyEvent(StandardEvent.COMPARE_LESS, node);
            node.setLeftChild(deleteRecursive((AVLTreeNode) node.getLeftChild(), value));
        } else if (value > node.getValue()) {
            notifyEvent(StandardEvent.COMPARE_GREATER, node);
            node.setRightChild(deleteRecursive((AVLTreeNode) node.getRightChild(), value));
        } else {
            // DELETE_SUCCESS
            notifyEvent(StandardEvent.DELETE_SUCCESS, node);
            if ((node.getLeftChild() == null) || (node.getRightChild() == null)) {
                AVLTreeNode temp = (AVLTreeNode) ((node.getLeftChild() != null) ? node.getLeftChild() : node.getRightChild());
                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                int smallestVal = findSmallestValue((BinaryTreeNode) node.getRightChild());
                node.changeValue(smallestVal);
                node.setRightChild(deleteRecursive((AVLTreeNode) node.getRightChild(), smallestVal));
            }
        }

        if (node == null) return null;

        // Cân bằng lại
        node.setHeight(Math.max(height(node.getLeftChild()), height(node.getRightChild())) + 1);
        notifyEvent(AVLEvent.UPDATE_HEIGHT, node);
        notifyEvent(AVLEvent.CALC_BF, node);

        AVLTreeNode result = rebalance(node);

        // TRẢ LẠI MÀU BÌNH THƯỜNG
        node.changeStatus(NodeStatus.NORMAL);
        notifyNodeChanged(node);

        return result;
    }
}