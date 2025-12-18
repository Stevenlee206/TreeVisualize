package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.ScapegoatTreeNode;
import java.util.ArrayList;
import java.util.List;

public class ScapegoatTree extends BinarySearchTree {
    private int n = 0; // Số lượng node thực tế
    private int q = 0; // Số lượng node lớn nhất từng đạt tới (dùng cho xóa)
    private final double alpha = 0.7;
    private boolean deleted;

    @Override
    public void insert(int value) {
        // 1. Thực hiện chèn và lấy độ sâu của node mới
        int depth = insertWithDepth(value);
        
        // 2. Kiểm tra điều kiện cân bằng alpha-height
        if (depth > Math.log(q) / Math.log(1.0 / alpha)) {
            // Tìm node vật tế thần (Scapegoat)
            BinaryTreeNode u = findNode(value);
            BinaryTreeNode w = findScapegoat(u);
            if (w != null) {
                rebuild(w);
            }
        }
    }


    private int insertWithDepth(int value) {
        if (root == null) {
            root = new ScapegoatTreeNode(value);
            n++; q++;
            notifyStructureChanged();
            return 0;
        }

        BinaryTreeNode curr = (BinaryTreeNode) root;
        int d = 0;
        while (true) {
            d++;
            if (value < curr.getValue()) {
                if (curr.getLeftChild() == null) {
                    curr.setLeftChild(new ScapegoatTreeNode(value));
                    break;
                }
                curr = (BinaryTreeNode) curr.getLeftChild();
            } else if (value > curr.getValue()) {
                if (curr.getRightChild() == null) {
                    curr.setRightChild(new ScapegoatTreeNode(value));
                    break;
                }
                curr = (BinaryTreeNode) curr.getRightChild();
            } else return d; // Đã tồn tại
        }
        n++; q++;
        notifyStructureChanged();
        return d;
    }

    private BinaryTreeNode findScapegoat(BinaryTreeNode u) {
        BinaryTreeNode w = u;
        BinaryTreeNode parent = findParent(w);
        while (parent != null) {
            int sizeW = getSize(w);
            int sizeP = getSize(parent);
            // Điều kiện Scapegoat: size(child) > alpha * size(parent)
            if ((double) sizeW > alpha * sizeP) {
                return parent;
            }
            w = parent;
            parent = findParent(w);
        }
        return null;
    }

    private void rebuild(BinaryTreeNode u) {
        List<BinaryTreeNode> nodes = new ArrayList<>();
        flatten(u, nodes); // Thu thập node theo In-order (đã sắp xếp)
        
        BinaryTreeNode p = findParent(u);
        BinaryTreeNode newNode = buildBalanced(nodes, 0, nodes.size() - 1);
        
        if (p == null) {
            root = newNode;
        } else if (p.getLeftChild() == u) {
            p.setLeftChild(newNode);
        } else {
            p.setRightChild(newNode);
        }
        notifyStructureChanged(); // Chụp ảnh animation sau khi rebuild
    }

    private void flatten(BinaryTreeNode u, List<BinaryTreeNode> nodes) {
        if (u == null) return;
        flatten((BinaryTreeNode) u.getLeftChild(), nodes);
        nodes.add(u);
        flatten((BinaryTreeNode) u.getRightChild(), nodes);
    }

    private BinaryTreeNode buildBalanced(List<BinaryTreeNode> nodes, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        BinaryTreeNode node = nodes.get(mid);
        
        node.setLeftChild(buildBalanced(nodes, start, mid - 1));
        node.setRightChild(buildBalanced(nodes, mid + 1, end));
        return node;
    }

    private int getSize(BinaryTreeNode u) {
        if (u == null) return 0;
        return 1 + getSize((BinaryTreeNode) u.getLeftChild()) + getSize((BinaryTreeNode) u.getRightChild());
    }

    // Hàm hỗ trợ tìm kiếm node theo giá trị
    private BinaryTreeNode findNode(int val) {
        BinaryTreeNode curr = (BinaryTreeNode) root;
        while (curr != null) {
            if (val == curr.getValue()) return curr;
            curr = (val < curr.getValue()) ? (BinaryTreeNode) curr.getLeftChild() : (BinaryTreeNode) curr.getRightChild();
        }
        return null;
    }

    // Hàm tìm cha của một node
    private BinaryTreeNode findParent(BinaryTreeNode child) {
        if (root == null || root == child) return null;
        BinaryTreeNode curr = (BinaryTreeNode) root;
        while (curr != null) {
            if (curr.getLeftChild() == child || curr.getRightChild() == child) return curr;
            curr = (child.getValue() < curr.getValue()) ? (BinaryTreeNode) curr.getLeftChild() : (BinaryTreeNode) curr.getRightChild();
        }
        return null;
    }

    @Override
    public void delete(int value) {
        deleted = false;
        root = deleteRecursive((BinaryTreeNode) root, value);
        if (deleted) {
            n--;   
            if (n < alpha * q) {
                rebuild((BinaryTreeNode) root); 
                q = n;
            }
        }
        notifyStructureChanged(); 
    }

    private BinaryTreeNode deleteRecursive(BinaryTreeNode current, int value) {
        if (current == null) return null;

        if (value < current.getValue()) {
            current.setLeftChild(deleteRecursive((BinaryTreeNode) current.getLeftChild(), value));
        } 
        else if (value > current.getValue()) {
            current.setRightChild(deleteRecursive((BinaryTreeNode) current.getRightChild(), value));
        } 
        else {
            // Tìm thấy node cần xóa
            deleted = true;
            // Trường hợp 0 hoặc 1 con
            if (current.getLeftChild() == null) return (BinaryTreeNode) current.getRightChild();
            if (current.getRightChild() == null) return (BinaryTreeNode) current.getLeftChild();

            // Trường hợp 2 con
            BinaryTreeNode successor = minNode((BinaryTreeNode) current.getRightChild());
            current.setValue(successor.getValue());
            current.setRightChild(deleteRecursive((BinaryTreeNode) current.getRightChild(), successor.getValue()));
        }
        return current;
    }

    private BinaryTreeNode minNode(BinaryTreeNode node) {
        while (node.getLeftChild() != null) {
            node = (BinaryTreeNode) node.getLeftChild();
        }
        return node;
    }
}