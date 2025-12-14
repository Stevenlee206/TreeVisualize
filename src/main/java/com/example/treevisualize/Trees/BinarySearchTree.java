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
        // Điểm dừng: Hết đường tìm hoặc đã tìm thấy
        if (current == null || current.getValue() == value) {
            return current;
        }

        // Logic rẽ nhánh đặc trưng của BST
        if (value < current.getValue()) {
            return searchRecursive(current.getLeftChild(), value);
        } else {
            return searchRecursive(current.getRightChild(), value);
        }
    }
    @Override
    public void insert(int value) {
        // Nếu cây rỗng, tạo Root
        if (root == null) {
            root = new BinaryTreeNode(value);
            notifyStructureChanged();
            return;
        }

        // Kiểm tra trùng lặp trước khi đệ quy (tùy chọn)
        if (search(value) != null) {
            notifyError("Giá trị " + value + " đã tồn tại trong BST!");
            return;
        }

        // Gọi hàm đệ quy để chèn
        insertRecursive((BinaryTreeNode) root, value);
        notifyStructureChanged();
    }

    private void insertRecursive(BinaryTreeNode current, int value) {
        // CASE 1: Chèn vào bên TRÁI (Nhỏ hơn cha)
        if (value < current.getValue()) {
            if (current.getLeftChild() == null) {
                // Tìm thấy chỗ trống -> Tạo node và nối dây
                // (Setter đã tự động xử lý child.setParent(current))
                current.setLeftChild(new BinaryTreeNode(value));
            } else {
                // Chưa trống -> Đi tiếp xuống dưới
                insertRecursive(current.getLeftChild(), value);
            }
        }
        // CASE 2: Chèn vào bên PHẢI (Lớn hơn cha)
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

        // Kiểm tra tồn tại trước
        if (search(value) == null) {
            notifyError("Không tìm thấy giá trị " + value + " để xóa.");
            return;
        }

        // Gọi hàm đệ quy và cập nhật lại Root (phòng trường hợp xóa Root)
        root = deleteRecursive((BinaryTreeNode) root, value);

        notifyStructureChanged();
    }

    private BinaryTreeNode deleteRecursive(BinaryTreeNode current, int value) {
        if (current == null) {
            return null;
        }

        // BƯỚC A: TÌM NODE CẦN XÓA
        if (value < current.getValue()) {
            // Đi sang trái -> Cập nhật lại con trái sau khi xóa xong
            BinaryTreeNode newLeft = deleteRecursive(current.getLeftChild(), value);
            current.setLeftChild(newLeft);
            return current;
        }
        else if (value > current.getValue()) {
            // Đi sang phải
            BinaryTreeNode newRight = deleteRecursive(current.getRightChild(), value);
            current.setRightChild(newRight);
            return current;
        }

        // BƯỚC B: ĐÃ TÌM THẤY NODE (current.value == value)
        else {
            // CASE 1: com.example.treevisualize.Node lá (Không có con)
            if (current.getLeftChild() == null && current.getRightChild() == null) {
                return null; // Trả về null để cha cắt đứt liên kết
            }

            // CASE 2: Chỉ có 1 con
            if (current.getLeftChild() == null) {
                return current.getRightChild(); // Con phải lên thay thế vị trí của cha
            }
            if (current.getRightChild() == null) {
                return current.getLeftChild(); // Con trái lên thay thế
            }

            // CASE 3: Có đủ 2 con (Phức tạp nhất)
            // Chiến thuật: Tìm "Người thừa kế" (Successor) - com.example.treevisualize.Node nhỏ nhất bên phải
            int smallestValue = findSmallestValue(current.getRightChild());

            // Chép giá trị của người thừa kế vào node hiện tại
            // (Lúc này node hiện tại mang giá trị mới, node cũ coi như đã mất)
            current.changeValue(smallestValue);

            // Xóa người thừa kế đi (Vì giá trị đã được chép lên trên rồi)
            BinaryTreeNode newRight = deleteRecursive(current.getRightChild(), smallestValue);
            current.setRightChild(newRight);

            return current;
        }
    }
    /**
     * Tìm giá trị nhỏ nhất trong một nhánh cây (Đi hết về bên trái).
     * Dùng cho thuật toán xóa Case 3.
     */
    private int findSmallestValue(BinaryTreeNode root) {
        int min = root.getValue();
        while (root.getLeftChild() != null) {
            min = root.getLeftChild().getValue();
            root = root.getLeftChild();
        }
        return min;
    }
}
