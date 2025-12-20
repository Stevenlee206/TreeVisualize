package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Visualizer.Events.StandardEvent; // Import Event

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree extends Tree { // Kế thừa AbstractTree (hoặc Tree tùy cấu trúc của bạn)

    public BinaryTree() {
        super();
    }

    // --- INSERT (Level-Order Traversal) ---
    @Override
    public void insert(int value) {
        notifyEvent(StandardEvent.START, root);

        BinaryTreeNode newNode = new BinaryTreeNode(value);
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            root = newNode;
            notifyEvent(StandardEvent.INSERT_SUCCESS, root);
            notifyStructureChanged();
            return;
        }

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add((BinaryTreeNode) root);

        while (!queue.isEmpty()) {
            BinaryTreeNode current = queue.poll();

            // Kiểm tra bên trái
            if (current.getLeftChild() == null) {
                notifyEvent(StandardEvent.FOUND_INSERT_POS, current); // Tìm thấy chỗ trống trái
                current.setLeftChild(newNode);
                notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
                notifyStructureChanged();
                return;
            } else {
                notifyEvent(StandardEvent.GO_LEFT, current); // Duyệt sang trái
                queue.add((BinaryTreeNode) current.getLeftChild());
            }

            // Kiểm tra bên phải
            if (current.getRightChild() == null) {
                notifyEvent(StandardEvent.FOUND_INSERT_POS, current); // Tìm thấy chỗ trống phải
                current.setRightChild(newNode);
                notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
                notifyStructureChanged();
                return;
            } else {
                notifyEvent(StandardEvent.GO_RIGHT, current); // Duyệt sang phải
                queue.add((BinaryTreeNode) current.getRightChild());
            }
        }
    }

    // --- DELETE ---
    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);

        if (root == null) return;

        BinaryTreeNode targetNode = (BinaryTreeNode) search(value);

        if (targetNode == null) {
            notifyError("Cannot delete: value " + value + " not found.");
            return;
        }

        notifyEvent(StandardEvent.DELETE_SUCCESS, targetNode);

        if (targetNode == root) {
            clear();
            notifyStructureChanged();
            return;
        }

        BinaryTreeNode parent = (BinaryTreeNode) targetNode.getParent();

        if (parent != null) {
            try {
                // Ngắt liên kết với cha
                if (parent.getLeftChild() == targetNode) parent.setLeftChild(null);
                else if (parent.getRightChild() == targetNode) parent.setRightChild(null);

                notifyStructureChanged();
            } catch (Exception e) {
                notifyError("Error while deleting: " + e.getMessage());
            }
        } else {
            notifyError("Structural error: Node " + value + " doesn't have parent (is not Root).");
        }
    }

    // --- SEARCH ---
    @Override
    public Node search(int value) {
        if (root == null) return null;
        return searchRecursive((BinaryTreeNode) root, value);
    }

    private BinaryTreeNode searchRecursive(BinaryTreeNode node, int value) {
        if (node == null) return null;

        if (node.getValue() == value) {
            notifyEvent(StandardEvent.COMPARE_LESS, node); // Tạm dùng event compare để highlight tìm thấy
            return node;
        }

        // Highlight đường đi tìm kiếm
        notifyEvent(StandardEvent.GO_LEFT, node);
        BinaryTreeNode leftResult = searchRecursive(node.getLeftChild(), value);
        if (leftResult != null) {
            return leftResult;
        }

        notifyEvent(StandardEvent.GO_RIGHT, node);
        return searchRecursive(node.getRightChild(), value);
    }

    // --- INSERT (Specific Parent - Hỗ trợ GeneralInserter nếu cần) ---
    public void insert(int parentVal, int childVal) {
        notifyEvent(StandardEvent.START, root);
        BinaryTreeNode parent = (BinaryTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Cannot find parent node with value: " + parentVal);
            return;
        }

        notifyEvent(StandardEvent.FOUND_INSERT_POS, parent);

        if (parent.getLeftChild() == null) {
            parent.setLeftChild(new BinaryTreeNode(childVal));
            notifyEvent(StandardEvent.INSERT_SUCCESS, parent.getLeftChild());
            notifyStructureChanged();
        }
        else if (parent.getRightChild() == null) {
            parent.setRightChild(new BinaryTreeNode(childVal));
            notifyEvent(StandardEvent.INSERT_SUCCESS, parent.getRightChild());
            notifyStructureChanged();
        }
        else {
            notifyError("Node " + parentVal + " already has 2 children. Cannot insert more!");
        }
    }

    // ĐÃ XÓA: getHeight() và getNodeCount()
    // Class cha Tree sẽ tự động xử lý việc này thông qua getChildren() của BinaryTreeNode.
}