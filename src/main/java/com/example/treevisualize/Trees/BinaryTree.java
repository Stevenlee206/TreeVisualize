package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree extends Tree{
    public BinaryTree() {
        super();
    }

    @Override
    public void insert(int value) {
        BinaryTreeNode newNode = new BinaryTreeNode(value);

        // Trường hợp 1: Cây rỗng -> Làm gốc
        if (root == null) {
            root = newNode;
            notifyStructureChanged();
            return;
        }

        // Trường hợp 2: Cây đã có dữ liệu -> Dùng hàng đợi (Queue) để tìm chỗ trống
        // (BFS - Breadth First Search)
        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add((BinaryTreeNode) root);

        while (!queue.isEmpty()) {
            BinaryTreeNode current = queue.poll();

            // A. Kiểm tra bên TRÁI
            if (current.getLeftChild() == null) {
                current.setLeftChild(newNode); // Đã có hàm nối dây parent trong setter
                notifyStructureChanged();
                return;
            } else {
                // Nếu đã có con trái, đẩy nó vào hàng đợi để xét tiếp
                queue.add(current.getLeftChild());
            }

            // B. Kiểm tra bên PHẢI
            if (current.getRightChild() == null) {
                current.setRightChild(newNode);
                notifyStructureChanged();
                return;
            } else {
                // Nếu đã có con phải, đẩy nó vào hàng đợi
                queue.add(current.getRightChild());
            }
        }
    }

    @Override
    public void delete(int value) {
        if (root == null) return;

        // 1. Tìm node cần xóa
        BinaryTreeNode targetNode = (BinaryTreeNode) search(value);

        if (targetNode == null) {
            notifyError("Không tìm thấy giá trị " + value + " để xóa.");
            return;
        }

        // 2. Trường hợp xóa Root
        if (targetNode == root) {
            clear();
            notifyStructureChanged();
            return;
        }

        // 3. Xử lý xóa node thường
        // Lấy cha (nhờ hàm getParent và ép kiểu)
        BinaryTreeNode parent = (BinaryTreeNode) targetNode.getParent();

        if (parent != null) {
            try {
                // Gọi hàm removeChild của BinaryTreeNode
                // (Hàm này bạn đã viết: kiểm tra trái/phải và gán null)
                parent.removeChild(targetNode);

                notifyStructureChanged();
            } catch (Exception e) {
                notifyError("Lỗi khi xóa: " + e.getMessage());
            }
        } else {
            notifyError("Lỗi cấu trúc: com.example.treevisualize.Node " + value + " không có cha (không phải Root).");
        }
    }

    @Override
    public Node search(int value) {
        if (root == null) return null;
        return searchRecursive((BinaryTreeNode) root, value);
    }
    private BinaryTreeNode searchRecursive(BinaryTreeNode node, int value) {
        if (node == null) return null;

        // 1. Kiểm tra chính node này
        if (node.getValue() == value) {
            return node;
        }

        // 2. Tìm bên trái
        BinaryTreeNode leftResult = searchRecursive(node.getLeftChild(), value);
        if (leftResult != null) {
            return leftResult;
        }

        // 3. Tìm bên phải
        return searchRecursive(node.getRightChild(), value);
    }
    /**
     * Thêm child vào parent cụ thể.
     * Quy tắc: Điền vào Left trước. Nếu Left có rồi thì điền vào Right.
     * Nếu cả 2 đều có -> Báo lỗi Full.
     */
    public void insert(int parentVal, int childVal) {
        // 1. Tìm node cha
        // (search trả về com.example.treevisualize.Node, cần ép kiểu về BinaryTreeNode)
        BinaryTreeNode parent = (BinaryTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Không tìm thấy node cha có giá trị: " + parentVal);
            return;
        }

        // 2. Kiểm tra slot bên TRÁI
        if (parent.getLeftChild() == null) {
            // Hàm setLeftChild trong BinaryTreeNode đã lo việc nối dây parent
            parent.setLeftChild(new BinaryTreeNode(childVal));
            notifyStructureChanged();
        }
        // 3. Kiểm tra slot bên PHẢI (nếu trái đã đầy)
        else if (parent.getRightChild() == null) {
            parent.setRightChild(new BinaryTreeNode(childVal));
            notifyStructureChanged();
        }
        // 4. Nếu cả 2 slot đều đầy
        else {
            notifyError("Node " + parentVal + " đã đủ 2 con. Không thể chèn thêm!");
        }
    }
}
