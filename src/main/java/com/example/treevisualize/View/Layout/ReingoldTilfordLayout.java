package com.example.treevisualize.View.Layout;

import com.example.treevisualize.View.Layout.Strategy.DefaultAlignmentStrategy;
import com.example.treevisualize.View.Layout.Strategy.NodeAlignmentStrategy;
import com.example.treevisualize.Model.Node.Node;
import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReingoldTilfordLayout implements TreeLayout {
    // Tăng khoảng cách để cây thoáng hơn
    private final double NODE_DIAMETER = 40;

    // Khoảng cách tối thiểu giữa 2 anh em ruột (Sibling)
    // Tăng lên để anh em không đứng sát rạt nhau
    private final double SIBLING_MARGIN = 20;

    // Khoảng cách tối thiểu giữa 2 nhánh cây con (Subtree)
    // Tăng cái này để các cụm cây tách hẳn nhau ra -> Cực kỳ quan trọng để cây thoáng
    private final double SUBTREE_MARGIN = 40;

    private final double LEVEL_GAP = 70;

    private final NodeAlignmentStrategy alignmentStrategy;

    public ReingoldTilfordLayout(NodeAlignmentStrategy strategy) {
        this.alignmentStrategy = strategy;
    }

    public ReingoldTilfordLayout() {
        this(new DefaultAlignmentStrategy());
    }

    @Override
    public Map<Node, Point2D> calculateLayout(Node root) {
        Map<Node, Point2D> positions = new HashMap<>();
        if (root == null) return positions;

        // Bước 1: Tính toán X tương đối (có xử lý va chạm)
        calculateX(root);

        // Bước 2: Căn chỉnh lại toạ độ X để không bị âm
        normalizeX(root);

        // Bước 3: Chuyển đổi sang Map kết quả
        collectPositions(root, 0, positions);

        return positions;
    }

    // --- LOGIC CHÍNH: Tính toán X đệ quy ---
    private void calculateX(Node node) {
        List<Node> children = node.getChildren();

        for (Node child : children) {
            calculateX(child);
        }

        // XỬ LÝ LÁ (LEAF) - Dùng SIBLING_MARGIN
        if (children.isEmpty()) {
            Node leftSibling = getLeftSibling(node);
            if (leftSibling != null) {
                // Cũ: getX(leftSibling) + NODE_SIZE
                // Mới: Cộng thêm margin để có khe hở
                setX(node, getX(leftSibling) + NODE_DIAMETER + SIBLING_MARGIN);
            } else {
                setX(node, 0);
            }
        }
        // XỬ LÝ NODE CHA - Dùng SUBTREE_MARGIN trong checkContourConflict
        else {
            // A. Quét đường bao để tránh va chạm giữa các cây con
            for (int i = 0; i < children.size() - 1; i++) {
                Node leftChild = children.get(i);
                Node rightChild = children.get(i + 1);

                // Gọi hàm check mới với khoảng cách đệm lớn hơn
                double shift = checkContourConflict(leftChild, rightChild);

                if (shift > 0) {
                    shiftSubtreeRight(children, i + 1, shift);
                    // Có thể thêm logic: centerTree(children) nếu muốn phân bố lại các con
                }
            }

            // B. Căn giữa cha (Giữ nguyên logic cũ)
            Node firstChild = children.get(0);
            Node lastChild = children.get(children.size() - 1);

            double firstX = getX(firstChild);
            double lastX = getX(lastChild);

            // Layout hỏi Strategy: "Tôi nên đặt ông bố này ở đâu?"
            double midPoint = alignmentStrategy.calculateParentX(node, firstX, lastX);
            Node leftSibling = getLeftSibling(node);
            if (leftSibling != null) {
                double expectedX = getX(leftSibling) + NODE_DIAMETER + SIBLING_MARGIN;
                if (midPoint < expectedX) {
                    double offset = expectedX - midPoint;
                    shiftSubtreeRight(children, 0, offset);
                    setX(node, expectedX);
                } else {
                    setX(node, midPoint);
                }
            } else {
                setX(node, midPoint);
            }
        }
    }
    /**
     * [THUẬT TOÁN QUAN TRỌNG NHẤT]
     * Kiểm tra va chạm đường bao (Contour) giữa cây con trái và cây con phải.
     * Trả về khoảng cách cần dịch chuyển (shift) để tách chúng ra.
     */
    private double checkContourConflict(Node leftNode, Node rightNode) {
        Map<Integer, Double> rightContourOfLeftTree = new HashMap<>();
        Map<Integer, Double> leftContourOfRightTree = new HashMap<>();

        getContour(leftNode, 0, rightContourOfLeftTree, true);
        getContour(rightNode, 0, leftContourOfRightTree, false);

        double maxShift = 0;

        for (Integer depth : rightContourOfLeftTree.keySet()) {
            if (leftContourOfRightTree.containsKey(depth)) {
                double leftLimit = rightContourOfLeftTree.get(depth);
                double rightLimit = leftContourOfRightTree.get(depth);

                // Khoảng cách thực tế giữa 2 nhánh
                double dist = rightLimit - leftLimit;

                // KHOẢNG CÁCH MONG MUỐN:
                // Phải bằng kích thước Node + Khoảng hở Subtree (cho thoáng)
                double minDistance = NODE_DIAMETER + SUBTREE_MARGIN;

                if (dist < minDistance) {
                    maxShift = Math.max(maxShift, minDistance - dist);
                }
            }
        }
        return maxShift;
    }

    // Hàm đệ quy lấy đường biên (Contour)
    private void getContour(Node node, int depth, Map<Integer, Double> contour, boolean isRightContour) {
        double currentX = getX(node);

        if (!contour.containsKey(depth)) {
            contour.put(depth, currentX);
        } else {
            double existing = contour.get(depth);
            if (isRightContour) {
                contour.put(depth, Math.max(existing, currentX)); // Lấy max X cho biên phải
            } else {
                contour.put(depth, Math.min(existing, currentX)); // Lấy min X cho biên trái
            }
        }

        for (Node child : node.getChildren()) {
            getContour(child, depth + 1, contour, isRightContour);
        }
    }

    private void shiftSubtreeRight(List<Node> siblings, int startIndex, double shift) {
        for (int i = startIndex; i < siblings.size(); i++) {
            Node n = siblings.get(i);
            shiftRecursive(n, shift); // Dịch chuyển node n và toàn bộ con cháu của nó
        }
    }

    private void shiftRecursive(Node node, double shift) {
        setX(node, getX(node) + shift);
        for (Node child : node.getChildren()) {
            shiftRecursive(child, shift);
        }
    }

    // --- Helper lưu trữ X tạm thời ---
    // (Trong thực tế nên dùng Map, nhưng để code gọn tôi giả lập getter/setter)
    // Bạn có thể thêm trường `tempX` vào Node hoặc dùng Map<Node, Double> bên ngoài.
    // Ở đây tôi dùng Map nội bộ cho an toàn:
    private Map<Node, Double> tempXMap = new HashMap<>();

    private void setX(Node n, double x) { tempXMap.put(n, x); }
    private double getX(Node n) { return tempXMap.getOrDefault(n, 0.0); }

    // --- Helper khác ---
    private Node getLeftSibling(Node node) {
        Node parent = node.getParent();
        if (parent == null) return null;
        List<Node> siblings = parent.getChildren();
        int idx = siblings.indexOf(node);
        if (idx > 0) return siblings.get(idx - 1);
        return null;
    }

    private void normalizeX(Node root) {
        double minX = Double.MAX_VALUE;
        for (double val : tempXMap.values()) minX = Math.min(minX, val);

        double shift = Math.abs(minX) + 50; // Padding 50px
        for (Map.Entry<Node, Double> entry : tempXMap.entrySet()) {
            entry.setValue(entry.getValue() + shift);
        }
    }

    private void collectPositions(Node node, int depth, Map<Node, Point2D> positions) {
        positions.put(node, new Point2D(getX(node), depth * LEVEL_GAP + 50));
        for (Node child : node.getChildren()) {
            collectPositions(child, depth + 1, positions);
        }
    }
}