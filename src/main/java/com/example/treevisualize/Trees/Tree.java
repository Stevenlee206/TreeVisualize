package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.NodeStatus;
import com.example.treevisualize.Visualizer.TreeObserver;

import java.util.ArrayList;
import java.util.List;


public abstract class Tree {
    protected Node root;
    private List<TreeObserver> observers;
    public Tree() {
        this.root = null;
        this.observers = new ArrayList<>();
    }
    public List<Node> traverse(TraversalStrategy strategy) {
        if (this.root == null) {
            return new ArrayList<>();
        }
        // Bước 1: Nhờ Strategy tính toán danh sách các node theo thứ tự
        List<Node> path = strategy.traverse(this.root);

        // Bước 2: Duyệt qua danh sách kết quả để ghi hình (Animation)
        // Đây là bước quan trọng để AnimationController chụp lại các frame
        for (Node node : path) {
            visit(node);
        }

        // Bước 3: Trả về danh sách (đúng như chữ ký bạn yêu cầu)
        return path;
    }
    public void addObserver(TreeObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(TreeObserver observer) {
        observers.remove(observer);
    }
    public abstract void insert(int value);

    public abstract void delete(int value);

    public abstract Node search(int value);

    public Node getRoot() {
        return root;
    }
    /**
     * Cập nhật root (Thường dùng cho tính năng Undo/Restore từ Snapshot).
     */
    public void setRoot(Node newRoot) {
        this.root = newRoot;
        notifyStructureChanged(); // Báo vẽ lại ngay lập tức
    }

    public void clear() {
        this.root = null;
        notifyStructureChanged();
    }

    protected void notifyStructureChanged() {
        for (TreeObserver observer : observers) {
            observer.onStructureChanged();
        }
    }

    /**
     * Gọi khi một node thay đổi trạng thái (Đổi màu, Highlight).
     * View sẽ vẽ lại node đó (hoặc toàn bộ) để hiển thị hiệu ứng.
     */
    protected void notifyNodeChanged(Node node) {
        for (TreeObserver observer : observers) {
            observer.onNodeChanged(node);
        }
    }

    /**
     * Gọi khi có lỗi logic xảy ra (VD: Chèn trùng số, Xóa số không tồn tại).
     * PseudoCodeBlock sẽ hiển thị dòng thông báo lỗi màu đỏ.
     */
    protected void notifyError(String message) {
        for (TreeObserver observer : observers) {
            observer.onError(message);
        }
    }

    protected void visit(Node node) {
        if (node == null) return;

        // BƯỚC 1: Sáng đèn (ACTIVE) - Màu CAM
        // Để báo hiệu: "Tôi đang đứng ở đây"
        node.changeStatus(NodeStatus.ACTIVE);
        notifyNodeChanged(node); // -> Chụp Snapshot 1

        // BƯỚC 2: [SỬA ĐỔI] Trả về màu gốc (NORMAL) ngay lập tức
        // Thay vì dùng VISITED (sẽ lưu vệt màu xanh), ta dùng NORMAL.
        // Khi status là NORMAL, Visualizer sẽ tự động vẽ lại màu Đỏ/Đen/Trắng gốc của node.
        node.changeStatus(NodeStatus.NORMAL);
        notifyNodeChanged(node); // -> Chụp Snapshot 2
    }

    /**
     * Hàm helper: Reset màu toàn bộ cây về trạng thái bình thường.
     * Cần gọi hàm này trước khi bắt đầu một lượt Animation mới.
     */
    public void resetTreeStatus() {
        if (root != null) {
            resetRecursive(root);
            notifyStructureChanged(); // Vẽ lại cây sạch sẽ
        }
    }

    private void resetRecursive(Node node) {
        if (node == null) return;

        // Trả về trạng thái bình thường (để Visualizer vẽ đúng màu Đỏ/Đen hoặc Trắng)
        node.changeStatus(NodeStatus.NORMAL);

        // Đệ quy reset cho các con
        // Lưu ý: Cần kiểm tra kiểu để gọi getChild cho đúng
        if (node instanceof com.example.treevisualize.Node.BinaryTreeNode) {
            var bNode = (com.example.treevisualize.Node.BinaryTreeNode) node;
            resetRecursive(bNode.getLeftChild());
            resetRecursive(bNode.getRightChild());
        }
        else if (node instanceof com.example.treevisualize.Node.GeneralTreeNode) {
            var gNode = (com.example.treevisualize.Node.GeneralTreeNode) node;
            resetRecursive(gNode.getLeftMostChild());
            resetRecursive(gNode.getRightSibling());
        }
    }
}
