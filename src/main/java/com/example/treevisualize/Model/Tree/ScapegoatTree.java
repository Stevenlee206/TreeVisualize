package com.example.treevisualize.Model.Tree;

import com.example.treevisualize.Model.Node.BinaryTreeNode;
import com.example.treevisualize.Model.Node.ScapegoatTreeNode;

import com.example.treevisualize.View.Visualizer.Events.ScapegoatEvent;
import com.example.treevisualize.View.Visualizer.Events.StandardEvent;
import com.example.treevisualize.Model.Node.Node;
import com.example.treevisualize.Model.Node.NodeStatus;

import java.util.ArrayList;
import java.util.List;

public class ScapegoatTree extends BinarySearchTree {
    private int n = 0; // Số phần tử hiện tại
    private int q = 0; // MaxSize (dùng để đo độ cân bằng alpha)
    private static final double ALPHA = 0.66;

    public ScapegoatTree() {
        super();
        this.n = 0;
        this.q = 0;
    }
 // --- SEARCH ---
    @Override
    public Node search(int value) {
        notifyEvent(StandardEvent.START, root);
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            return null;
        }
        // Call the recursive visual search
        return searchRecursive((ScapegoatTreeNode) root, value);
    }

    private ScapegoatTreeNode searchRecursive(ScapegoatTreeNode current, int value) {
        if (current == null) return null;

        // 1. Visual Highlight
        current.changeStatus(NodeStatus.ACTIVE);
        notifyNodeChanged(current);
        notifyEvent(StandardEvent.SEARCH_CHECK, current);

        // 2. Found
        if (current.getValue() == value) {
            notifyEvent(StandardEvent.SEARCH_FOUND, current);
            current.changeStatus(NodeStatus.NORMAL);
            notifyNodeChanged(current);
            return current;
        }

        ScapegoatTreeNode result;
        // 3. Binary Search Logic
        if (value < current.getValue()) {
            notifyEvent(StandardEvent.COMPARE_LESS, current);
            notifyEvent(StandardEvent.GO_LEFT, current);
            
            // Cleanup current node visual before moving down
            current.changeStatus(NodeStatus.NORMAL);
            notifyNodeChanged(current);
            
            result = searchRecursive((ScapegoatTreeNode) current.getLeftChild(), value);
        } else {
            notifyEvent(StandardEvent.COMPARE_GREATER, current);
            notifyEvent(StandardEvent.GO_RIGHT, current);
            
            current.changeStatus(NodeStatus.NORMAL);
            notifyNodeChanged(current);
            
            result = searchRecursive((ScapegoatTreeNode) current.getRightChild(), value);
        }

        return result;
    }

    @Override
    public void insert(int value) {
        notifyEvent(StandardEvent.START, root);

        // 1. Insert chuẩn BST
        ScapegoatTreeNode insertedNode = insertAndGetNode(value);
        if (insertedNode == null) return; // Trùng giá trị

        // 2. Cập nhật size tổ tiên
        notifyEvent(ScapegoatEvent.UPDATE_SIZE, insertedNode);
        updateAncestorsSize(insertedNode);

        // 3. Kiểm tra độ sâu (Điều kiện Scapegoat)
        notifyEvent(ScapegoatEvent.CHECK_DEPTH, insertedNode);
        if (getDepth(insertedNode) > Math.log(q) / Math.log(1.0 / ALPHA)) {

            // 4. Tìm Scapegoat
            notifyEvent(ScapegoatEvent.FIND_SCAPEGOAT, insertedNode);
            ScapegoatTreeNode scapegoat = findScapegoat(insertedNode);

            if (scapegoat != null) {
                // 5. Xây lại cây con
                notifyEvent(ScapegoatEvent.REBUILD_START, scapegoat);
                rebuild(scapegoat);
            }
        }
        notifyStructureChanged();
    }

    private ScapegoatTreeNode insertAndGetNode(int value) {
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            root = new ScapegoatTreeNode(value);
            notifyEvent(StandardEvent.INSERT_SUCCESS, root);
            n++; q++;
            return (ScapegoatTreeNode) root;
        }

        ScapegoatTreeNode curr = (ScapegoatTreeNode) root;
        while (true) {
            if (value < curr.getValue()) {
                notifyEvent(StandardEvent.COMPARE_LESS, curr);
                if (curr.getLeftChild() == null) {
                    notifyEvent(StandardEvent.FOUND_INSERT_POS, curr);
                    ScapegoatTreeNode newNode = new ScapegoatTreeNode(value);
                    curr.setLeftChild(newNode);
                    newNode.setParent(curr); // Quan trọng
                    notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
                    n++; q++;
                    return newNode;
                }
                notifyEvent(StandardEvent.GO_LEFT, curr);
                curr = (ScapegoatTreeNode) curr.getLeftChild();
            } else if (value > curr.getValue()) {
                notifyEvent(StandardEvent.COMPARE_GREATER, curr);
                if (curr.getRightChild() == null) {
                    notifyEvent(StandardEvent.FOUND_INSERT_POS, curr);
                    ScapegoatTreeNode newNode = new ScapegoatTreeNode(value);
                    curr.setRightChild(newNode);
                    newNode.setParent(curr);
                    notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
                    n++; q++;
                    return newNode;
                }
                notifyEvent(StandardEvent.GO_RIGHT, curr);
                curr = (ScapegoatTreeNode) curr.getRightChild();
            } else {
                return null; // Duplicate
            }
        }
    }

    private void updateAncestorsSize(ScapegoatTreeNode node) {
        while (node.getParent() != null) {
            node = (ScapegoatTreeNode) node.getParent();
            node.updateSize();
        }
        // Update root size nếu cần (thường root.parent == null)
        if (root != null && root instanceof ScapegoatTreeNode) {
            ((ScapegoatTreeNode)root).updateSize();
        }
    }

    private int getDepth(ScapegoatTreeNode node) {
        int depth = 0;
        while (node.getParent() != null) {
            node = (ScapegoatTreeNode) node.getParent();
            depth++;
        }
        return depth;
    }

    private ScapegoatTreeNode findScapegoat(ScapegoatTreeNode u) {
        ScapegoatTreeNode current = u;
        while (current.getParent() != null) {
            ScapegoatTreeNode parent = (ScapegoatTreeNode) current.getParent();
            double sizeCurr = current.getSize();
            double sizeParent = parent.getSize();

            if (sizeCurr > ALPHA * sizeParent) {
                return parent;
            }
            current = parent;
        }
        return null;
    }

    private void rebuild(ScapegoatTreeNode u) {
        List<ScapegoatTreeNode> nodes = new ArrayList<>();
        flatten(u, nodes);

        BinaryTreeNode parent = (BinaryTreeNode) u.getParent();
        ScapegoatTreeNode newSubtreeRoot = buildBalanced(nodes, 0, nodes.size() - 1);

        if (parent == null) {
            root = newSubtreeRoot;
            if (newSubtreeRoot != null) newSubtreeRoot.setParent(null);
        } else {
            if (parent.getLeftChild() == u) {
                parent.setLeftChild(newSubtreeRoot);
            } else {
                parent.setRightChild(newSubtreeRoot);
            }
            if (newSubtreeRoot != null) newSubtreeRoot.setParent(parent);
        }
    }

    private void flatten(ScapegoatTreeNode u, List<ScapegoatTreeNode> nodes) {
        if (u == null) return;
        flatten((ScapegoatTreeNode) u.getLeftChild(), nodes);
        nodes.add(u);
        flatten((ScapegoatTreeNode) u.getRightChild(), nodes);
    }

    private ScapegoatTreeNode buildBalanced(List<ScapegoatTreeNode> nodes, int start, int end) {
        if (start > end) return null;

        int mid = (start + end) / 2;
        ScapegoatTreeNode node = nodes.get(mid);

        // Reset liên kết để xây lại
        node.setLeftChild(null);
        node.setRightChild(null);

        node.setLeftChild(buildBalanced(nodes, start, mid - 1));
        if (node.getLeftChild() != null) node.getLeftChild().setParent(node);

        node.setRightChild(buildBalanced(nodes, mid + 1, end));
        if (node.getRightChild() != null) node.getRightChild().setParent(node);

        node.updateSize();

        return node;
    }
    // --- DELETE ---

    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);
        if (search(value) == null) return;

        root = deleteRecursive((ScapegoatTreeNode) root, value);

        // Kiểm tra điều kiện trọng số
        notifyEvent(ScapegoatEvent.CHECK_WEIGHT, root);
        if (n < ALPHA * q) {
            if (root != null) {
                notifyEvent(ScapegoatEvent.REBUILD_START, root);
                rebuild((ScapegoatTreeNode) root);
                q = n;
            }
        }
        notifyStructureChanged();
    }

    private ScapegoatTreeNode deleteRecursive(ScapegoatTreeNode node, int value) {
        if (node == null) return null;

        if (value < node.getValue()) {
            notifyEvent(StandardEvent.COMPARE_LESS, node);
            node.setLeftChild(deleteRecursive((ScapegoatTreeNode) node.getLeftChild(), value));
        } else if (value > node.getValue()) {
            notifyEvent(StandardEvent.COMPARE_GREATER, node);
            node.setRightChild(deleteRecursive((ScapegoatTreeNode) node.getRightChild(), value));
        } else {
            notifyEvent(StandardEvent.DELETE_SUCCESS, node);
            // Tìm thấy node
            if (node.getLeftChild() == null) {
                n--;
                return (ScapegoatTreeNode) node.getRightChild();
            }
            if (node.getRightChild() == null) {
                n--;
                return (ScapegoatTreeNode) node.getLeftChild();
            }

            // Case 2 con
            int smallestVal = findSmallestValue((BinaryTreeNode) node.getRightChild());
            node.changeValue(smallestVal);

            n++; // Trick giữ n không đổi khi gọi đệ quy giả
            node.setRightChild(deleteRecursive((ScapegoatTreeNode) node.getRightChild(), smallestVal));
        }

        if (node != null) {
            node.updateSize();
        }

        return node;
    }
}