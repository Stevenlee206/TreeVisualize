package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.NodeColor;
import com.example.treevisualize.Node.RedBlackTreeNode;
import com.example.treevisualize.Node.BinaryTreeNode;

import com.example.treevisualize.Visualizer.Events.StandardEvent;
import com.example.treevisualize.Visualizer.Events.RBTEvent;

public class RedBlackTree extends BinarySearchTree {

    public RedBlackTree() {
        super();
    }

    // --- HELPER METHODS ---
    private boolean isRed(RedBlackTreeNode node) {
        return node != null && node.getColor() == NodeColor.RED;
    }

    private void setColor(Node node, NodeColor color) {
        if (node != null && node instanceof RedBlackTreeNode) {
            ((RedBlackTreeNode) node).changeColor(color);
            notifyNodeChanged(node);

            if (color == NodeColor.RED) notifyEvent(RBTEvent.PAINT_RED, node);
            else notifyEvent(RBTEvent.PAINT_BLACK, node);
        }
    }

    private RedBlackTreeNode parentOf(Node node) {
        return node == null ? null : (RedBlackTreeNode) node.getParent();
    }

    private RedBlackTreeNode leftOf(Node node) {
        return node == null ? null : (RedBlackTreeNode) ((BinaryTreeNode) node).getLeftChild();
    }

    private RedBlackTreeNode rightOf(Node node) {
        return node == null ? null : (RedBlackTreeNode) ((BinaryTreeNode) node).getRightChild();
    }

    // --- INSERT LOGIC ---
    @Override
    public void insert(int value) {
        notifyEvent(StandardEvent.START, root);

        if (root == null) {
            RedBlackTreeNode newRoot = new RedBlackTreeNode(value);
            newRoot.changeColor(NodeColor.BLACK);
            this.root = newRoot;
            notifyEvent(StandardEvent.INSERT_SUCCESS, newRoot);
            notifyEvent(RBTEvent.PAINT_BLACK, newRoot);
            notifyStructureChanged();
            return;
        }

        if (search(value) != null) {
            notifyError("The value " + value + " already exists!");
            return;
        }

        RedBlackTreeNode newNode = new RedBlackTreeNode(value);
        newNode.changeColor(NodeColor.RED);
        notifyEvent(RBTEvent.PAINT_RED, newNode);

        insertStandardBST(newNode);

        notifyEvent(RBTEvent.FIXUP_START, newNode);
        fixInsert(newNode);

        notifyStructureChanged();
    }

    private void insertStandardBST(RedBlackTreeNode newNode) {
        RedBlackTreeNode current = (RedBlackTreeNode) root;
        RedBlackTreeNode parent = null;

        while (current != null) {
            parent = current;
            if (newNode.getValue() < current.getValue()) {
                notifyEvent(StandardEvent.COMPARE_LESS, current);
                notifyEvent(StandardEvent.GO_LEFT, current);
                current = leftOf(current);
            } else {
                notifyEvent(StandardEvent.COMPARE_GREATER, current);
                notifyEvent(StandardEvent.GO_RIGHT, current);
                current = rightOf(current);
            }
        }

        if (newNode.getValue() < parent.getValue()) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }
        notifyEvent(StandardEvent.INSERT_SUCCESS, newNode);
    }

    private void fixInsert(RedBlackTreeNode k) {
        RedBlackTreeNode u;

        while (k != root && isRed(parentOf(k))) {
            if (parentOf(k) == leftOf(parentOf(parentOf(k)))) {
                u = rightOf(parentOf(parentOf(k)));

                if (isRed(u)) { // Case 1: Recolor
                    notifyEvent(RBTEvent.CASE_1, k);
                    setColor(parentOf(k), NodeColor.BLACK);
                    setColor(u, NodeColor.BLACK);
                    setColor(parentOf(parentOf(k)), NodeColor.RED);
                    k = parentOf(parentOf(k));
                } else {
                    if (k == rightOf(parentOf(k))) { // Case 2: Rotate Left
                        notifyEvent(RBTEvent.CASE_2, k);
                        k = parentOf(k);
                        leftRotate(k);
                    }
                    // Case 3: Rotate Right
                    notifyEvent(RBTEvent.CASE_3, k);
                    setColor(parentOf(k), NodeColor.BLACK);
                    setColor(parentOf(parentOf(k)), NodeColor.RED);
                    rightRotate(parentOf(parentOf(k)));
                }
            } else {
                u = leftOf(parentOf(parentOf(k)));

                if (isRed(u)) { // Case 1
                    notifyEvent(RBTEvent.CASE_1, k);
                    setColor(parentOf(k), NodeColor.BLACK);
                    setColor(u, NodeColor.BLACK);
                    setColor(parentOf(parentOf(k)), NodeColor.RED);
                    k = parentOf(parentOf(k));
                } else {
                    if (k == leftOf(parentOf(k))) { // Case 2
                        notifyEvent(RBTEvent.CASE_2, k);
                        k = parentOf(k);
                        rightRotate(k);
                    }
                    // Case 3
                    notifyEvent(RBTEvent.CASE_3, k);
                    setColor(parentOf(k), NodeColor.BLACK);
                    setColor(parentOf(parentOf(k)), NodeColor.RED);
                    leftRotate(parentOf(parentOf(k)));
                }
            }
        }
        setColor(root, NodeColor.BLACK);
    }

    // --- DELETE LOGIC ---
    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);

        RedBlackTreeNode z = (RedBlackTreeNode) search(value);
        if (z == null) {
            notifyError("Cannot delete: value " + value + " not found.");
            return;
        }

        notifyEvent(StandardEvent.DELETE_SUCCESS, z);

        RedBlackTreeNode x, y;

        if (leftOf(z) == null || rightOf(z) == null) {
            y = z;
        } else {
            y = successor(z);
        }

        if (leftOf(y) != null) {
            x = leftOf(y);
        } else {
            x = rightOf(y);
        }

        RedBlackTreeNode yParent = parentOf(y);

        if (x != null) {
            x.setParent(yParent);
        }

        if (yParent == null) {
            this.root = x;
        } else if (y == leftOf(yParent)) {
            yParent.setLeftChild(x);
        } else {
            yParent.setRightChild(x);
        }

        if (y != z) {
            z.changeValue(y.getValue());
        }

        if (!isRed(y)) {
            notifyEvent(RBTEvent.FIXUP_START, x);
            fixDelete(x, yParent);
        }

        notifyStructureChanged();
    }

    private void fixDelete(RedBlackTreeNode x, RedBlackTreeNode xParent) {
        RedBlackTreeNode w;

        while (x != root && !isRed(x)) {

            if (x == leftOf(xParent)) {
                w = rightOf(xParent);

                if (isRed(w)) {
                    notifyEvent(RBTEvent.CASE_2, w); // Case: Brother Red -> Rotate
                    setColor(w, NodeColor.BLACK);
                    setColor(xParent, NodeColor.RED);
                    leftRotate(xParent);
                    w = rightOf(xParent);
                }

                if (!isRed(leftOf(w)) && !isRed(rightOf(w))) {
                    notifyEvent(RBTEvent.CASE_1, w); // Case: Black brother, black nephews
                    setColor(w, NodeColor.RED);
                    x = xParent;
                    xParent = parentOf(x);
                } else {
                    if (!isRed(rightOf(w))) {
                        notifyEvent(RBTEvent.CASE_2, w);
                        setColor(leftOf(w), NodeColor.BLACK);
                        setColor(w, NodeColor.RED);
                        rightRotate(w);
                        w = rightOf(xParent);
                    }
                    notifyEvent(RBTEvent.CASE_3, w);
                    setColor(w, isRed(xParent) ? NodeColor.RED : NodeColor.BLACK);
                    setColor(xParent, NodeColor.BLACK);
                    setColor(rightOf(w), NodeColor.BLACK);
                    leftRotate(xParent);
                    x = (RedBlackTreeNode) root;
                    xParent = null;
                }
            } else {
                w = leftOf(xParent);

                if (isRed(w)) {
                    notifyEvent(RBTEvent.CASE_2, w);
                    setColor(w, NodeColor.BLACK);
                    setColor(xParent, NodeColor.RED);
                    rightRotate(xParent);
                    w = leftOf(xParent);
                }

                if (!isRed(rightOf(w)) && !isRed(leftOf(w))) {
                    notifyEvent(RBTEvent.CASE_1, w);
                    setColor(w, NodeColor.RED);
                    x = xParent;
                    xParent = parentOf(x);
                } else {
                    if (!isRed(leftOf(w))) {
                        notifyEvent(RBTEvent.CASE_2, w);
                        setColor(rightOf(w), NodeColor.BLACK);
                        setColor(w, NodeColor.RED);
                        leftRotate(w);
                        w = leftOf(xParent);
                    }
                    notifyEvent(RBTEvent.CASE_3, w);
                    setColor(w, isRed(xParent) ? NodeColor.RED : NodeColor.BLACK);
                    setColor(xParent, NodeColor.BLACK);
                    setColor(leftOf(w), NodeColor.BLACK);
                    rightRotate(xParent);
                    x = (RedBlackTreeNode) root;
                    xParent = null;
                }
            }
        }
        if (x != null) setColor(x, NodeColor.BLACK);
    }

    private void leftRotate(RedBlackTreeNode x) {
        notifyEvent(RBTEvent.ROTATE_LEFT, x);

        if (x == null || rightOf(x) == null) return;
        RedBlackTreeNode y = rightOf(x);
        x.setRightChild(y.getLeftChild());

        if (y.getLeftChild() != null) {
            y.getLeftChild().setParent(x);
        }

        y.setParent(parentOf(x));

        if (parentOf(x) == null) {
            this.root = y;
        } else if (x == leftOf(parentOf(x))) {
            parentOf(x).setLeftChild(y);
        } else {
            parentOf(x).setRightChild(y);
        }

        y.setLeftChild(x);
        x.setParent(y);
    }

    private void rightRotate(RedBlackTreeNode y) {
        notifyEvent(RBTEvent.ROTATE_RIGHT, y);

        if (y == null || leftOf(y) == null) return;
        RedBlackTreeNode x = leftOf(y);
        y.setLeftChild(x.getRightChild());

        if (x.getRightChild() != null) {
            x.getRightChild().setParent(y);
        }

        x.setParent(parentOf(y));

        if (parentOf(y) == null) {
            this.root = x;
        } else if (y == rightOf(parentOf(y))) {
            parentOf(y).setRightChild(x);
        } else {
            parentOf(y).setLeftChild(x);
        }

        x.setRightChild(y);
        y.setParent(x);
    }

    private RedBlackTreeNode successor(RedBlackTreeNode node) {
        RedBlackTreeNode temp = rightOf(node);
        while (leftOf(temp) != null) {
            temp = leftOf(temp);
        }
        return temp;
    }
}