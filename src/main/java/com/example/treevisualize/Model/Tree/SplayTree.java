package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.BinaryTreeNode;
import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.View.Visualizer.Events.SplayEvent;

public class SplayTree extends BinarySearchTree {

    public SplayTree() {
        super();
    }

    /**
     * Override Search: Tìm xong thì Splay node đó lên gốc
     */
    @Override
    public Node search(int value) {
        // notifyEvent(StandardEvent.START, root); // Search BST đã có notify bên trong
        Node found = super.search(value);
        if (found != null) {
            notifyEvent(SplayEvent.SPLAY_START, found);
            splay((BinaryTreeNode) found);
        }
        return found;
    }

    /**
     * Override Insert: Chèn xong thì Splay node mới lên gốc
     */
    @Override
    public void insert(int value) {
        super.insert(value); // Tận dụng logic chèn & notify của BST cũ

        // Sau khi insert xong, tìm lại node đó để splay
        // Lưu ý: super.insert đã notifyStructureChanged, nên visualizer đã vẽ xong bước insert.
        // Giờ ta splay tiếp.
        Node newNode = super.search(value);
        if (newNode != null) {
            notifyEvent(SplayEvent.SPLAY_START, newNode);
            splay((BinaryTreeNode) newNode);
        }
    }

    /**
     * Override Delete: Xóa xong Splay cha của node bị xóa
     */
    @Override
    public void delete(int value) {
        BinaryTreeNode nodeToDelete = (BinaryTreeNode) super.search(value);
        if (nodeToDelete == null) {
            // Logic báo lỗi đã có trong super.delete
            super.delete(value);
            return;
        }

        BinaryTreeNode parent = (BinaryTreeNode) nodeToDelete.getParent();
        super.delete(value);

        if (parent != null) {
            notifyEvent(SplayEvent.SPLAY_START, parent);
            splay(parent);
        } else if (root != null) {
            // Nếu xóa root cũ, root mới có thể cần splay (tùy biến thể, ở đây splay root mới cho chắc)
            notifyEvent(SplayEvent.SPLAY_START, root);
            splay((BinaryTreeNode) root);
        }
    }

    // --- LOGIC SPLAY (Zig, Zag, Zig-Zig...) ---
    private void splay(BinaryTreeNode node) {
        while (node.getParent() != null) {
            BinaryTreeNode parent = (BinaryTreeNode) node.getParent();
            BinaryTreeNode grandParent = (BinaryTreeNode) parent.getParent();

            if (grandParent == null) {
                // Trường hợp 1: Cha là Root -> Xoay đơn (Zig hoặc Zag)
                notifyEvent(SplayEvent.CASE_ZIG, node);
                if (node == parent.getLeftChild()) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else {
                // Trường hợp 2: Có Ông nội
                if (node == parent.getLeftChild() && parent == grandParent.getLeftChild()) {
                    // Zig-Zig (Cùng bên trái) -> Xoay Ông trước, Cha sau
                    notifyEvent(SplayEvent.CASE_ZIG_ZIG, node);
                    rotateRight(grandParent);
                    rotateRight(parent);
                } else if (node == parent.getRightChild() && parent == grandParent.getRightChild()) {
                    // Zag-Zag (Cùng bên phải)
                    notifyEvent(SplayEvent.CASE_ZIG_ZIG, node);
                    rotateLeft(grandParent);
                    rotateLeft(parent);
                } else if (node == parent.getRightChild() && parent == grandParent.getLeftChild()) {
                    // Zig-Zag (Gấp khúc)
                    notifyEvent(SplayEvent.CASE_ZIG_ZAG, node);
                    rotateLeft(parent);
                    rotateRight(grandParent);
                } else {
                    // Zag-Zig (Gấp khúc)
                    notifyEvent(SplayEvent.CASE_ZIG_ZAG, node);
                    rotateRight(parent);
                    rotateLeft(grandParent);
                }
            }
        }
        notifyStructureChanged(); // Báo hiệu vẽ lại cây sau khi xoay xong toàn bộ
    }

    private void rotateRight(BinaryTreeNode p) {
        notifyEvent(SplayEvent.ROTATE_RIGHT, p); // Highlight xoay phải

        BinaryTreeNode x = p.getLeftChild();
        if (x == null) return;

        BinaryTreeNode T2 = x.getRightChild();
        BinaryTreeNode grandParent = (BinaryTreeNode) p.getParent();

        // 1. Hạ p xuống làm con phải của x
        x.setRightChild(p);
        p.setLeftChild(T2);

        // 2. Cập nhật cha cho x
        updateParent(grandParent, p, x);

        // Cập nhật parent cho các node con bị chuyển chỗ
        if (T2 != null) T2.setParent(p);
        p.setParent(x);
        x.setParent(grandParent);
    }

    private void rotateLeft(BinaryTreeNode p) {
        notifyEvent(SplayEvent.ROTATE_LEFT, p); // Highlight xoay trái

        BinaryTreeNode x = p.getRightChild();
        if (x == null) return;

        BinaryTreeNode T2 = x.getLeftChild();
        BinaryTreeNode grandParent = (BinaryTreeNode) p.getParent();

        // 1. Hạ p xuống làm con trái của x
        x.setLeftChild(p);
        p.setRightChild(T2);

        // 2. Cập nhật cha cho x
        updateParent(grandParent, p, x);

        // Cập nhật parent cho các node con bị chuyển chỗ
        if (T2 != null) T2.setParent(p);
        p.setParent(x);
        x.setParent(grandParent);
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