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

    private Map<Node, NodeVisualizer> nodeVis;
    public static final double NODE_RADIUS = 15.0;
    public static final double VERTICAL_GAP = 80.0;

    // --- CONSTRUCTOR ---
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
        render();
    }

    @Override
    public void onStructureChanged() {
        nodeVis.clear();
        render();
    }

    @Override
    public void onError(String message) {
        System.err.println("TreeVisualizer Error: " + message);
    }

    /**
     * Hàm chính để vẽ lại toàn bộ cây.
     */
    public void render() {
        // 1. Lấy chiều cao cây
        if (tree == null || tree.getRoot() == null) {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            return;
        }
        int height = tree.getHeight();

        // 2. TÍNH TOÁN KÍCH THƯỚC CẦN THIẾT 
        // Khoảng cách ngang tối thiểu ở tầng dưới cùng (để 2 node lá không đè lên nhau)
        // Node radius = 20 -> Đường kính 40 -> Cần ít nhất 50-60px khoảng cách
        double minGapAtBottom = NODE_RADIUS * 2 + 3; 
        
        // Tính khoảng cách offset ban đầu cho Root dựa trên chiều cao cây
        // Công thức: Gap tại root = MinGap * 2^(height - 2)
        // Ví dụ: Cây cao 1 (chỉ root) -> shift 0
        // Cây cao 2 (root + con) -> shift = minGap
        double initialHGap = minGapAtBottom * Math.pow(2, Math.max(0, height - 2));

        // Tính chiều rộng tổng thể cần thiết:
        // Root ở giữa, cây xòe ra 2 bên -> Width = initialHGap * 4 (ước lượng an toàn)
        // Hoặc tính theo số lượng lá tối đa: 2^(h-1) * minGapAtBottom
        double requiredWidth = Math.max(1000, initialHGap * 4 + 100); 
        double requiredHeight = Math.max(600, height * VERTICAL_GAP + 100);

        // 3. CẬP NHẬT KÍCH THƯỚC CANVAS
        // Bước này cực kỳ quan trọng để ScrollPane hoạt động
        canvas.setWidth(requiredWidth);
        canvas.setHeight(requiredHeight);

        // 4. Xóa sạch màn hình (Sau khi resize)
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // 5. Tính toán toạ độ bắt đầu
        // Luôn bắt đầu từ chính giữa Canvas hiện tại
        double startX = canvas.getWidth() / 2;
        double startY = NODE_RADIUS * 2;

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

        // Khoảng cách cho tầng tiếp theo (giảm dần nhưng không quá nhỏ)
        double nextHGap = hGap * 0.5;

        // Đệ quy TRÁI
        if (left != null) {
            double leftX = x - hGap;
            double nextY = y + VERTICAL_GAP;
            gc.strokeLine(x, y, leftX, nextY);
            calculateLayout(left, leftX, nextY, nextHGap);
        }

        // Đệ quy PHẢI
        if (right != null) {
            double rightX = x + hGap;
            double nextY = y + VERTICAL_GAP;
            gc.strokeLine(x, y, rightX, nextY);
            calculateLayout(right, rightX, nextY, nextHGap);
        }

        // B5: Cuối cùng mới vẽ Node (để đè lên dây)
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
        if (node.getStatus() != null && node.getStatus() != com.example.treevisualize.Node.NodeStatus.NORMAL) {
            switch (node.getStatus()) {
                case ACTIVE:
                    vis.setFillColor(Color.ORANGE);
                    return;
                case VISITED:
                    vis.setFillColor(Color.LIGHTBLUE);
                    return;
                case FOUND:
                    vis.setFillColor(Color.LIMEGREEN);
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
            // Với BinaryTree hoặc BST thường -> Màu xanh
            vis.setFillColor(Color.GREEN);
        }
    }
    
    public Canvas getCanvas() {
        return this.canvas;
    }
}