package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.Node;

public class BinarySearchTree extends Tree{
    public BinarySearchTree() {
        super();
    }

    @Override
    public Node search(int value) {
        return searchRecursive((BinaryTreeNode) root, value);
    }

    private BinaryTreeNode searchRecursive(BinaryTreeNode current, int value) {
        if (current == null || current.getValue() == value) {
            return current;
        }
        if (value < current.getValue()) {
            return searchRecursive(current.getLeftChild(), value);
        } else {
            return searchRecursive(current.getRightChild(), value);
        }
    }
    @Override
    public void insert(int value) {
        if (root == null) {
            root = new BinaryTreeNode(value);
            notifyStructureChanged();
            return;
        }
        if (search(value) != null) {
            notifyError("Giá trị " + value + " đã tồn tại trong BST!");
            return;
        }
        insertRecursive((BinaryTreeNode) root, value);
        notifyStructureChanged();
    }

    private void insertRecursive(BinaryTreeNode current, int value) {
        if (value < current.getValue()) {
            if (current.getLeftChild() == null) {
                current.setLeftChild(new BinaryTreeNode(value));
            } else {
                insertRecursive(current.getLeftChild(), value);
            }
        }
        else if (value > current.getValue()) {
            if (current.getRightChild() == null) {
                current.setRightChild(new BinaryTreeNode(value));
            } else {
                insertRecursive(current.getRightChild(), value);
            }
        }
    }

    @Override
    public void delete(int value) {
        if (root == null) return;
        if (search(value) == null) {
            notifyError("Không tìm thấy giá trị " + value + " để xóa.");
            return;
        }
        root = deleteRecursive((BinaryTreeNode) root, value);

        notifyStructureChanged();
    }

    private BinaryTreeNode deleteRecursive(BinaryTreeNode current, int value) {
        if (current == null) {
            return null;
        }
        if (value < current.getValue()) {
            BinaryTreeNode newLeft = deleteRecursive(current.getLeftChild(), value);
            current.setLeftChild(newLeft);
            return current;
        }
        else if (value > current.getValue()) {
            BinaryTreeNode newRight = deleteRecursive(current.getRightChild(), value);
            current.setRightChild(newRight);
            return current;
        }
        else {
            if (current.getLeftChild() == null && current.getRightChild() == null) {
                return null;
            }
            if (current.getLeftChild() == null) {
                return current.getRightChild();
            }
            if (current.getRightChild() == null) {
                return current.getLeftChild();
            }

            // CASE 3: Có đủ 2 con (Phức tạp nhất)
            // Chiến thuật: Tìm "Người thừa kế" (Successor) - com.example.treevisualize.Node nhỏ nhất bên phải
            int smallestValue = findSmallestValue(current.getRightChild());
            current.changeValue(smallestValue);
            BinaryTreeNode newRight = deleteRecursive(current.getRightChild(), smallestValue);
            current.setRightChild(newRight);

            return current;
        }
    }
    /**
     * Tìm giá trị nhỏ nhất trong một nhánh cây (Đi hết về bên trái).
     */
    private int findSmallestValue(BinaryTreeNode root) {
        int min = root.getValue();
        while (root.getLeftChild() != null) {
            min = root.getLeftChild().getValue();
            root = root.getLeftChild();
        }
        return min;
    }

    public int getHeight() {
        return height(root);
    }

    private int height(Node node) {
        if (node == null) return 0;
        // Binary Tree / BST / RBT
        if (node instanceof BinaryTreeNode) {
            var b = (BinaryTreeNode) node;
            return 1 + Math.max(
                    height(b.getLeftChild()),
                    height(b.getRightChild())
            );
        }
        return 1;
    }

    public int getNodeCount() {
        return countNodes(root);
    }

    private int countNodes(Node node) {
        if (node == null) return 0;

        // Binary Tree / BST / RBT
        if (node instanceof BinaryTreeNode) {
            var b = (BinaryTreeNode) node;
            return 1 + countNodes(b.getLeftChild()) + countNodes(b.getRightChild());
        }
        return 1;
    }
}
