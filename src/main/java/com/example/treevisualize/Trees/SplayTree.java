package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.Node;

public class SplayTree extends BinarySearchTree {

    public SplayTree() {
        super();
    }

    /**
     * Override Search: Tìm xong thì Splay node đó lên gốc
     */
    @Override
    public Node search(int value) {
        Node found = super.search(value);
        if (found != null) {
            splay((BinaryTreeNode) found);
        }
        return found;
    }

    /**
     * Override Insert: Chèn xong thì Splay node mới lên gốc
     */
    @Override
    public void insert(int value) {
        super.insert(value); // Tận dụng logic chèn của BST cũ
        Node newNode = super.search(value); // Lấy lại node vừa chèn
        if (newNode != null) {
            splay((BinaryTreeNode) newNode);
        }
    }

    /**
     * Override Delete: Xóa xong Splay cha của node bị xóa
     */
    @Override
    public void delete(int value) {
        BinaryTreeNode nodeToDelete = (BinaryTreeNode) super.search(value);
        if (nodeToDelete == null) return;

        BinaryTreeNode parent = (BinaryTreeNode) nodeToDelete.getParent();
        super.delete(value);

        if (parent != null) {
            // Splay cha của node vừa bị xóa (hoặc node thay thế vị trí đó)
            // Để đơn giản, ta splay root hiện tại để cân bằng lại
            if (root != null) splay((BinaryTreeNode) root);
        }
    }

    // --- LOGIC SPLAY (Zig, Zag, Zig-Zig...) ---
    private void splay(BinaryTreeNode node) {
        while (node.getParent() != null) {
            BinaryTreeNode parent = (BinaryTreeNode) node.getParent();
            BinaryTreeNode grandParent = (BinaryTreeNode) parent.getParent();

            if (grandParent == null) {
                // Trường hợp 1: Cha là Root -> Xoay đơn (Zig hoặc Zag)
                if (node == parent.getLeftChild()) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else {
                // Trường hợp 2: Có Ông nội
                if (node == parent.getLeftChild() && parent == grandParent.getLeftChild()) {
                    // Zig-Zig (Cùng bên trái) -> Xoay Ông trước, Cha sau
                    rotateRight(grandParent);
                    rotateRight(parent);
                } else if (node == parent.getRightChild() && parent == grandParent.getRightChild()) {
                    // Zag-Zag (Cùng bên phải)
                    rotateLeft(grandParent);
                    rotateLeft(parent);
                } else if (node == parent.getRightChild() && parent == grandParent.getLeftChild()) {
                    // Zig-Zag (Gấp khúc)
                    rotateLeft(parent);
                    rotateRight(grandParent);
                } else {
                    // Zag-Zig (Gấp khúc)
                    rotateRight(parent);
                    rotateLeft(grandParent);
                }
            }
        }
        notifyStructureChanged(); // Báo hiệu vẽ lại cây sau khi xoay
    }

    private void rotateRight(BinaryTreeNode p) {
        BinaryTreeNode x = p.getLeftChild();
        if (x == null) return;

        BinaryTreeNode T2 = x.getRightChild();
        BinaryTreeNode grandParent = (BinaryTreeNode) p.getParent();

        // 1. Hạ p xuống làm con phải của x
        x.setRightChild(p);
        p.setLeftChild(T2);

        // 2. Cập nhật cha cho x
        updateParent(grandParent, p, x);
    }

    private void rotateLeft(BinaryTreeNode p) {
        BinaryTreeNode x = p.getRightChild();
        if (x == null) return;

        BinaryTreeNode T2 = x.getLeftChild();
        BinaryTreeNode grandParent = (BinaryTreeNode) p.getParent();

        // 1. Hạ p xuống làm con trái của x
        x.setLeftChild(p);
        p.setRightChild(T2);

        // 2. Cập nhật cha cho x
        updateParent(grandParent, p, x);
    }

    private void updateParent(BinaryTreeNode grandParent, BinaryTreeNode oldChild, BinaryTreeNode newChild) {
        if (grandParent == null) {
            this.root = newChild;
            if (newChild != null) newChild.setParent(null);
        } else {
            if (grandParent.getLeftChild() == oldChild) {
                grandParent.setLeftChild(newChild);
            } else {
                grandParent.setRightChild(newChild);
            }
        }
    }
}