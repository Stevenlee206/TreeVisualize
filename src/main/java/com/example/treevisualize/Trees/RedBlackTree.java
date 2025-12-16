package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.NodeColor;
import com.example.treevisualize.Node.RedBlackTreeNode;
import com.example.treevisualize.Node.BinaryTreeNode;
public class RedBlackTree extends BinarySearchTree {
    public RedBlackTree() {
        super();
    }

    private boolean isRed(RedBlackTreeNode node) {
        if (node == null) {
            return false;
        }
        return node.getColor() == NodeColor.RED;
    }

    private void setColor(Node node, NodeColor color) {
        if (node != null && node instanceof RedBlackTreeNode) {
            ((RedBlackTreeNode) node).changeColor(color);
            notifyNodeChanged(node);
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

    @Override
    public void insert(int value) {
        if (root == null) {
            RedBlackTreeNode newRoot = new RedBlackTreeNode(value);
            newRoot.changeColor(NodeColor.BLACK);
            this.root = newRoot;
            notifyStructureChanged();
            return;
        }

        if (search(value) != null) {
            notifyError("Giá trị " + value + " đã tồn tại!");
            return;
        }
        RedBlackTreeNode newNode = new RedBlackTreeNode(value);
        newNode.changeColor(NodeColor.RED);

        insertStandardBST(newNode);

        // Fixup
        fixInsert(newNode);
        notifyStructureChanged();
    }
    private void insertStandardBST(RedBlackTreeNode newNode) {
        RedBlackTreeNode current = (RedBlackTreeNode) root;
        RedBlackTreeNode parent = null;

        while (current != null) {
            parent = current;
            if (newNode.getValue() < current.getValue()) {
                current = leftOf(current);
            } else {
                current = rightOf(current);
            }
        }

        if (newNode.getValue() < parent.getValue()) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }
    }

    private void fixInsert(RedBlackTreeNode k) {
        RedBlackTreeNode u;

        while (k != root && isRed(parentOf(k))) {

            // A. Cha là con TRÁI của Ông
            if (parentOf(k) == leftOf(parentOf(parentOf(k)))) {
                u = rightOf(parentOf(parentOf(k)));

                if (isRed(u)) { // Case 1: Chú ĐỎ
                    setColor(parentOf(k), NodeColor.BLACK);
                    setColor(u, NodeColor.BLACK);
                    setColor(parentOf(parentOf(k)), NodeColor.RED);
                    k = parentOf(parentOf(k));
                } else {
                    if (k == rightOf(parentOf(k))) { // Case 2: Chú ĐEN, k là con Phải
                        k = parentOf(k);
                        leftRotate(k);
                    }
                    // Case 3: Chú ĐEN, k là con Trái
                    setColor(parentOf(k), NodeColor.BLACK);
                    setColor(parentOf(parentOf(k)), NodeColor.RED);
                    rightRotate(parentOf(parentOf(k)));
                }
            }
            // B. Cha là con PHẢI của Ông
            else {
                u = leftOf(parentOf(parentOf(k)));

                if (isRed(u)) {
                    setColor(parentOf(k), NodeColor.BLACK);
                    setColor(u, NodeColor.BLACK);
                    setColor(parentOf(parentOf(k)), NodeColor.RED);
                    k = parentOf(parentOf(k));
                } else {
                    if (k == leftOf(parentOf(k))) {
                        k = parentOf(k);
                        rightRotate(k);
                    }
                    setColor(parentOf(k), NodeColor.BLACK);
                    setColor(parentOf(parentOf(k)), NodeColor.RED);
                    leftRotate(parentOf(parentOf(k)));
                }
            }
        }
        setColor(root, NodeColor.BLACK);
    }

    @Override
    public void delete(int value) {
        RedBlackTreeNode z = (RedBlackTreeNode) search(value);
        if (z == null) {
            notifyError("Không tìm thấy giá trị " + value);
            return;
        }

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

        // Fix nếu mất node ĐEN
        if (!isRed(y)) {
            fixDelete(x, yParent);
        }

        notifyStructureChanged();
    }

    private void fixDelete(RedBlackTreeNode x, RedBlackTreeNode xParent) {
        RedBlackTreeNode w;

        while (x != root && !isRed(x)) {

            // A. x là con TRÁI
            if (x == leftOf(xParent)) {
                w = rightOf(xParent);

                if (isRed(w)) { // Case 1
                    setColor(w, NodeColor.BLACK);
                    setColor(xParent, NodeColor.RED);
                    leftRotate(xParent);
                    w = rightOf(xParent);
                }

                if (!isRed(leftOf(w)) && !isRed(rightOf(w))) { // Case 2
                    setColor(w, NodeColor.RED);
                    x = xParent;
                    xParent = parentOf(x);
                } else {
                    if (!isRed(rightOf(w))) { // Case 3
                        setColor(leftOf(w), NodeColor.BLACK);
                        setColor(w, NodeColor.RED);
                        rightRotate(w);
                        w = rightOf(xParent);
                    }
                    // Case 4
                    setColor(w, isRed(xParent) ? NodeColor.RED : NodeColor.BLACK);
                    setColor(xParent, NodeColor.BLACK);
                    setColor(rightOf(w), NodeColor.BLACK);
                    leftRotate(xParent);
                    x = (RedBlackTreeNode) root;
                    xParent = null;
                }
            }
            // B. x là con PHẢI
            else {
                w = leftOf(xParent);

                if (isRed(w)) { // Case 1
                    setColor(w, NodeColor.BLACK);
                    setColor(xParent, NodeColor.RED);
                    rightRotate(xParent);
                    w = leftOf(xParent);
                }

                if (!isRed(rightOf(w)) && !isRed(leftOf(w))) { // Case 2
                    setColor(w, NodeColor.RED);
                    x = xParent;
                    xParent = parentOf(x);
                } else {
                    if (!isRed(leftOf(w))) { // Case 3
                        setColor(rightOf(w), NodeColor.BLACK);
                        setColor(w, NodeColor.RED);
                        leftRotate(w);
                        w = leftOf(xParent);
                    }
                    // Case 4
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
    // Rotation
    private void leftRotate(RedBlackTreeNode x) {
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