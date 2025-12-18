package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.ScapegoatTreeNode;

import java.util.ArrayList;
import java.util.List;

public class ScapegoatTree extends BinarySearchTree {
    private int n = 0;
    private int q = 0;
    private static final double ALPHA = 0.66;

    public ScapegoatTree() {
        super();
        this.n = 0;
        this.q = 0;
    }

    @Override
    public void insert(int value) {
        ScapegoatTreeNode insertedNode = insertAndGetNode(value);
        if (insertedNode == null) return; // Trùng giá trị

        updateAncestorsSize(insertedNode);

        if (getDepth(insertedNode) > Math.log(q) / Math.log(1.0 / ALPHA)) {
            ScapegoatTreeNode scapegoat = findScapegoat(insertedNode);

            if (scapegoat != null) {
                rebuild(scapegoat);
            }
        }
        notifyStructureChanged();
    }

    private ScapegoatTreeNode insertAndGetNode(int value) {
        if (root == null) {
            root = new ScapegoatTreeNode(value);
            n++; q++;
            return (ScapegoatTreeNode) root;
        }

        ScapegoatTreeNode curr = (ScapegoatTreeNode) root;
        while (true) {
            if (value < curr.getValue()) {
                if (curr.getLeftChild() == null) {
                    ScapegoatTreeNode newNode = new ScapegoatTreeNode(value);
                    curr.setLeftChild(newNode);
                    n++; q++;
                    return newNode;
                }
                curr = (ScapegoatTreeNode) curr.getLeftChild();
            } else if (value > curr.getValue()) {
                if (curr.getRightChild() == null) {
                    ScapegoatTreeNode newNode = new ScapegoatTreeNode(value);
                    curr.setRightChild(newNode);
                    n++; q++;
                    return newNode;
                }
                curr = (ScapegoatTreeNode) curr.getRightChild();
            } else {
                return null; // Duplicate
            }
        }
    }

    private void updateAncestorsSize(ScapegoatTreeNode node) {
        while (node.getParent() != null) {
            node = (ScapegoatTreeNode) node.getParent();
            node.updateSize(); // Node tự tính lại size của nó
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

        node.setLeftChild(null);
        node.setRightChild(null);

        node.setLeftChild(buildBalanced(nodes, start, mid - 1));
        node.setRightChild(buildBalanced(nodes, mid + 1, end));
        node.updateSize();

        return node;
    }

    @Override
    public void delete(int value) {
        if (search(value) == null) return;
        root = deleteRecursive((ScapegoatTreeNode) root, value);
        if (n < ALPHA * q) {
            if (root != null) {
                rebuild((ScapegoatTreeNode) root);
                q = n;
            }
        }
        notifyStructureChanged();
    }

    private ScapegoatTreeNode deleteRecursive(ScapegoatTreeNode node, int value) {
        if (node == null) return null;

        if (value < node.getValue()) {
            node.setLeftChild(deleteRecursive((ScapegoatTreeNode) node.getLeftChild(), value));
        } else if (value > node.getValue()) {
            node.setRightChild(deleteRecursive((ScapegoatTreeNode) node.getRightChild(), value));
        } else {
            // Tìm thấy node
            if (node.getLeftChild() == null) {
                n--; // Giảm số lượng thực tế
                return (ScapegoatTreeNode) node.getRightChild();
            }
            if (node.getRightChild() == null) {
                n--;
                return (ScapegoatTreeNode) node.getLeftChild();
            }

            // Case 2 con
            // findSmallestValue lấy từ BinarySearchTree (đã được đổi thành protected)
            int smallestVal = findSmallestValue((BinaryTreeNode) node.getRightChild());
            node.changeValue(smallestVal);

            // Mẹo: tăng n lên 1 đơn vị giả trước khi gọi đệ quy
            // Vì đệ quy bên dưới sẽ giảm n xuống, ta cần giữ n ổn định cho đến khi xóa thật
            n++;
            node.setRightChild(deleteRecursive((ScapegoatTreeNode) node.getRightChild(), smallestVal));
        }

        // [QUAN TRỌNG] Cập nhật size trên đường quay lui
        if (node != null) {
            node.updateSize();
        }

        return node;
    }
}