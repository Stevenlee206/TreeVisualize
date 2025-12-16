package com.example.treevisualize.Visualizer;
import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.NodeColor;
import com.example.treevisualize.Node.RedBlackTreeNode;
import com.example.treevisualize.Trees.Tree;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import com.example.treevisualize.Node.BinaryTreeNode;
import com.example.treevisualize.Node.GeneralTreeNode;
public class TreeVisualizer implements TreeObserver {

    private Canvas canvas;
    private GraphicsContext gc;
    private Tree tree;

    // Map lưu trữ NodeVisualizer (Key: com.example.treevisualize.Node, Value: NodeVisualizer)
    private Map<Node, NodeVisualizer> nodeVis;

    // Các hằng số static (gạch chân trong UML)
    public static final double NODE_RADIUS = 20.0;
    public static final double VERTICAL_GAP = 25.0;

    // --- CONSTRUCTOR ---
    /**
     * Khởi tạo com.example.treevisualize.Visualizer với cây và canvas có sẵn.
     */
    public TreeVisualizer(Tree tree, Canvas canvas) {
        this.tree = tree;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.nodeVis = new HashMap<>();
        this.tree.addObserver(this);
        render();
    }

    // --- IMPLEMENT TREE OBSERVER ---

    @Override
    public void onNodeChanged(Node node) {
        // Khi một node thay đổi (VD: đổi màu), ta vẽ lại
        // (Trong tương lai có thể tối ưu chỉ vẽ lại vùng node đó)
        render();
    }

    @Override
    public void onStructureChanged() {
        // Khi cấu trúc cây thay đổi (thêm/xóa), map cũ không còn đúng -> Clear
        nodeVis.clear();
        render();
    }

    @Override
    public void onError(String message) {
        // Xử lý lỗi (com.example.treevisualize.Visualizer có thể bỏ qua hoặc log ra console)
        System.err.println("TreeVisualizer Error: " + message);
    }

    // --- CORE LOGIC METHODS (Theo UML) ---

    /**
     * Hàm chính để vẽ lại toàn bộ cây.
     */
    public void render() {
        // 1. Xóa sạch màn hình
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        if (tree == null || tree.getRoot() == null) {
            return;
        }

        // 2. Tính toán toạ độ bắt đầu (Giữa màn hình, cách lề trên)
        double startX = canvas.getWidth() / 2;
        double startY = NODE_RADIUS * 2.5;
        double initialHGap = canvas.getWidth() / 4;

        // 3. Bắt đầu tính toán layout và vẽ đệ quy
        calculateLayout(tree.getRoot(), startX, startY, initialHGap);
    }

    /**
     * Hàm đệ quy tính toán vị trí và vẽ các thành phần.
     */
    private void calculateLayout(Node node, double x, double y, double hGap) {
        if (node == null) return;

        // B1: Đồng bộ hóa NodeVisualizer (đảm bảo node này đã có visual)
        syncNodeVisualizer(node);
        NodeVisualizer vis = nodeVis.get(node);

        // B2: Cập nhật dữ liệu cho visual
        vis.updatePosition(x, y);
        vis.setValue(node.getValue());

        // B3: Cập nhật màu sắc (Tách ra hàm riêng theo UML)
        updateNodeColor(vis, node);

        // B4: Xử lý vẽ Dây nối (Edge) & Đệ quy con
        Node left = null;
        Node right = null;

        if (node instanceof BinaryTreeNode) {
            left = ((BinaryTreeNode) node).getLeftChild();
            right = ((BinaryTreeNode) node).getRightChild();
        } else if (node instanceof GeneralTreeNode) {
            left = ((GeneralTreeNode) node).getLeftMostChild();
            right = ((GeneralTreeNode) node).getRightSibling();
        }

        // Cài đặt nét vẽ dây
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);

        // Đệ quy TRÁI
        if (left != null) {
            gc.strokeLine(x, y, x - hGap, y + VERTICAL_GAP);
            calculateLayout(left, x - hGap, y + VERTICAL_GAP, hGap / 2);
        }

        // Đệ quy PHẢI
        if (right != null) {
            gc.strokeLine(x, y, x + hGap, y + VERTICAL_GAP);
            calculateLayout(right, x + hGap, y + VERTICAL_GAP, hGap / 2);
        }

        // B5: Cuối cùng mới vẽ com.example.treevisualize.Node (để đè lên dây)
        vis.draw(gc);
    }

    /**
     * Kiểm tra và tạo mới NodeVisualizer nếu chưa tồn tại trong Map.
     */
    private void syncNodeVisualizer(Node node) {
        if (!nodeVis.containsKey(node)) {
            NodeVisualizer vis = new NodeVisualizer();
            vis.setRadius(NODE_RADIUS); // Sử dụng hằng số static
            nodeVis.put(node, vis);
        }
    }

    /**
     * Cập nhật màu sắc cho NodeVisualizer dựa trên NodeStatus và Loại Node
     */
    private void updateNodeColor(NodeVisualizer vis, Node node) {
        // 1. ƯU TIÊN CAO NHẤT: Kiểm tra NodeStatus (Trạng thái hoạt động)
        // Nếu status khác NORMAL, tức là đang có hoạt động (Duyệt, Tìm kiếm...)
        if (node.getStatus() != null && node.getStatus() != com.example.treevisualize.Node.NodeStatus.NORMAL) {
            switch (node.getStatus()) {
                case ACTIVE:
                    vis.setFillColor(Color.ORANGE); // Node đang đứng tại đó
                    return; // Thoát ngay, không xét Đỏ/Đen nữa
                case VISITED:
                    vis.setFillColor(Color.LIGHTBLUE); // Node đã đi qua (Vệt đường đi)
                    return;
                case FOUND:
                    vis.setFillColor(Color.LIMEGREEN); // Tìm thấy (Search)
                    return;
                case DELETED:
                    vis.setFillColor(Color.GRAY);
                    return;
            }
        }

        // 2. ƯU TIÊN THẤP HƠN: Màu sắc cấu trúc (Red/Black hoặc Trắng)
        if (node instanceof RedBlackTreeNode) {
            RedBlackTreeNode rbNode = (RedBlackTreeNode) node;
            if (rbNode.getColor() == NodeColor.RED) {
                vis.setFillColor(Color.RED);
            } else {
                vis.setFillColor(Color.BLACK);
            }
        } else {
            // Với BinaryTree hoặc BST thường -> Màu trắng
            vis.setFillColor(Color.GREEN);
        }
    }
    public Canvas getCanvas() {
        return this.canvas;
    }
}
