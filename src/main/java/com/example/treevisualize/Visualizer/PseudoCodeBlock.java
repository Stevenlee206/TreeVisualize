package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class    PseudoCodeBlock implements TreeObserver {
    private VBox container;
    private Label titleLabel;
    private VBox codeContentBox; // Hộp chứa các dòng code (tách biệt với tiêu đề)
    private List<Label> lineLabels;
    private Label statusLabel;

    // Header: Font có chân (Serif) giống LaTeX
    private final String STYLE_HEADER =
            "-fx-font-family: 'Times New Roman'; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: #000000;";

    // Dòng code thường: Font Monospaced để căn lề chuẩn
    private final String STYLE_NORMAL =
            "-fx-background-color: transparent; " +
                    "-fx-text-fill: #000000; " +
                    "-fx-font-family: 'Consolas', 'Menlo', 'Courier New'; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 1 5 1 5;";

    // Dòng highlight: Nền xám nhẹ (kiểu sách) hoặc xanh nhạt
    private final String STYLE_HIGHLIGHT =
            "-fx-background-color: #e0e0e0; " + // Xám nhạt giống highlight văn bản
                    "-fx-text-fill: #000000; " +
                    "-fx-font-family: 'Consolas', 'Menlo', 'Courier New'; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 1 5 1 5;";

    // Lỗi: Màu đỏ
    private final String STYLE_ERROR =
            "-fx-background-color: #ffebee; " +
                    "-fx-text-fill: #c62828; " +
                    "-fx-font-family: 'Consolas'; " +
                    "-fx-font-size: 13px; " +
                    "-fx-padding: 5;";

    public PseudoCodeBlock(VBox container) {
        this.container = container;
        this.lineLabels = new ArrayList<>();

        // 1. Cấu hình Container chính (Giống 1 tờ giấy)
        this.container.setSpacing(5);
        this.container.setPadding(new Insets(10));
        // Nền trắng, Viền đen mỏng
        this.container.setStyle("-fx-background-color: white; -fx-border-color: #333; -fx-border-width: 1px;");

        // 2. Tạo Tiêu đề (Algorithm Name)
        this.titleLabel = new Label("Algorithm");
        this.titleLabel.setStyle(STYLE_HEADER);

        // 3. Đường kẻ ngang trên (Top Separator)
        Separator topSep = new Separator();
        topSep.setStyle("-fx-background-color: black;");

        // 4. Hộp chứa nội dung code
        this.codeContentBox = new VBox(2); // Khoảng cách dòng code là 2px

        // 5. Đường kẻ ngang dưới (Bottom Separator)
        Separator botSep = new Separator();
        botSep.setStyle("-fx-background-color: black;");

        // 6. Label lỗi
        this.statusLabel = new Label();
        this.statusLabel.setStyle(STYLE_ERROR);
        this.statusLabel.setMaxWidth(Double.MAX_VALUE);
        this.statusLabel.setVisible(false);

        // Thêm tất cả vào container
        this.container.getChildren().addAll(titleLabel, topSep, codeContentBox, botSep, statusLabel);
    }

    /**
     * Cập nhật tiêu đề và nội dung thuật toán
     */
    public void setCode(String title, List<String> lines) {
        // Cập nhật tên thuật toán
        this.titleLabel.setText("Algorithm: " + title);

        // Xóa nội dung cũ
        this.codeContentBox.getChildren().clear();
        this.lineLabels.clear();
        this.statusLabel.setVisible(false);

        // Thêm từng dòng code mới
        for (String lineContent : lines) {
            Label label = new Label(lineContent);
            label.setStyle(STYLE_NORMAL);
            label.setMaxWidth(Double.MAX_VALUE);
            // Quan trọng: Giữ nguyên khoảng trắng (Space) để thụt đầu dòng
            label.setWrapText(false);

            this.lineLabels.add(label);
            this.codeContentBox.getChildren().add(label);
        }
    }

    // Giữ nguyên hàm cũ để tương thích (nếu lỡ gọi đâu đó)
    public void setCode(List<String> lines) {
        setCode("Procedure", lines);
    }

    public void highlightLine(int lineIndex) {
        if (lineIndex < 0 || lineIndex >= lineLabels.size()) return;
        this.statusLabel.setVisible(false);

        for (int i = 0; i < lineLabels.size(); i++) {
            Label lbl = lineLabels.get(i);
            if (i == lineIndex) {
                lbl.setStyle(STYLE_HIGHLIGHT);
            } else {
                lbl.setStyle(STYLE_NORMAL);
            }
        }
    }

    public void clearHighlight() {
        for (Label lbl : lineLabels) {
            lbl.setStyle(STYLE_NORMAL);
        }
        this.statusLabel.setVisible(false);
    }

    // --- TREE OBSERVER IMPLEMENTATION ---
    @Override
    public void onNodeChanged(Node node) { }

    @Override
    public void onStructureChanged() {
        clearHighlight();
        this.statusLabel.setVisible(false);
    }

    @Override
    public void onError(String message) {
        this.statusLabel.setText("⚠ " + message);
        this.statusLabel.setVisible(true);
    }
}