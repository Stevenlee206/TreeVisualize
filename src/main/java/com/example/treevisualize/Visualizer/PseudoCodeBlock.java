package com.example.treevisualize.Visualizer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import com.example.treevisualize.Node.Node;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PseudoCodeBlock implements TreeObserver {
    private VBox container;
    // Danh sách các Label để quản lý từng dòng (giúp truy xuất nhanh theo index)
    private List<Label> lineLabels;
    // Label đặc biệt dùng để hiện thông báo lỗi (từ Tree gửi sang)
    private Label statusLabel;
    // --- STYLES (CSS Inline) ---
    // Style cho dòng code bình thường (Nền trong suốt, chữ đen, font đơn cách)
    public final String STYLE_NORMAL=
            "-fx-background-color: transparent; " +
                    "-fx-text-fill: #333333; " +
                    "-fx-font-family: 'Consolas', 'Monospaced'; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 2 5 2 5;"; // Padding: Top Right Bottom Left;

    // Style cho dòng được Highlight (Nền xanh, chữ trắng, in đậm)
    private final String STYLE_HIGHLIGHT =
            "-fx-background-color: #2E8B57; " + // SeaGreen
                    "-fx-text-fill: white; " +
                    "-fx-font-family: 'Consolas', 'Monospaced'; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 2 5 2 5;";

    // Style cho dòng lỗi (Nền đỏ nhạt) - Tuỳ chọn
    private final String STYLE_ERROR =
            "-fx-background-color: #FFCDD2; " +
                    "-fx-text-fill: #C62828; " +
                    "-fx-font-family: 'Consolas', 'Monospaced'; " +
                    "-fx-padding: 2 5 2 5;";

    public PseudoCodeBlock(VBox container) {
        this.container = container;
        this.lineLabels = new ArrayList<>();

        // Cấu hình giao diện chung
        this.container.setSpacing(2);
        this.container.setPadding(new Insets(10));
        this.container.setBackground(new Background(new BackgroundFill(Color.web("#F4F4F4"), null, null)));

        // Khởi tạo label thông báo (mặc định ẩn)
        this.statusLabel = new Label();
        this.statusLabel.setStyle(STYLE_ERROR);
        this.statusLabel.setWrapText(true); // Tự xuống dòng nếu lỗi dài
        this.statusLabel.setVisible(false); // Ẩn đi khi chưa có lỗi
    }
    public void setCode(List<String> lines) {
        this.container.getChildren().clear();
        this.lineLabels.clear();
        this.statusLabel.setVisible(false); // Reset lỗi cũ

        for (String lineContent : lines) {
            Label label = new Label(lineContent);
            label.setStyle(STYLE_NORMAL);
            label.setMaxWidth(Double.MAX_VALUE);

            this.lineLabels.add(label);
            this.container.getChildren().add(label);
        }

        // Thêm statusLabel vào cuối cùng (để hiện lỗi dưới đáy)
        this.container.getChildren().add(statusLabel);
    }

    public void setCode(String title, List<String> lines) {
        this.container.getChildren().clear();
        this.lineLabels.clear();
        this.statusLabel.setVisible(false);

        // 1. Thêm tiêu đề (Title) vào đầu danh sách
        Label titleLabel = new Label(">>> " + title.toUpperCase());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2c3e50; -fx-font-size: 15px; -fx-padding: 5;");
        this.container.getChildren().add(titleLabel);

        // 2. Duyệt qua các dòng code (như hàm cũ của bạn)
        for (String line : lines) {
            Label lineLabel = new Label(line);
            lineLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: #333333; " +
                            "-fx-font-family: 'Consolas', 'Monospaced'; -fx-font-size: 14px; -fx-padding: 2 5 2 5;");
            lineLabel.setMaxWidth(Double.MAX_VALUE);
            this.lineLabels.add(lineLabel);
            this.container.getChildren().add(lineLabel);
        }

        this.container.getChildren().add(this.statusLabel);
    }

    public void highlightLine(int lineIndex) {
        if (lineIndex < 0 || lineIndex >= lineLabels.size()) return;

        // Xóa thông báo lỗi khi bắt đầu bước mới
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

    // --- IMPLEMENTS TREE OBSERVER (Phần mới thêm) ---

    @Override
    public void onNodeChanged(Node node) {
        // PseudoCodeBlock thường không quan tâm node đổi màu
        // Nên có thể để trống hoặc log ra console để debug
        // System.out.println("CodeBlock received: com.example.treevisualize.Node changed " + node.getValue());
    }

    @Override
    public void onStructureChanged() {
        // Khi cây thay đổi cấu trúc (Insert xong), ta nên clear highlight
        // để báo hiệu thuật toán đã kết thúc.
        clearHighlight();

        // Ẩn thông báo lỗi (nếu có) vì thao tác đã thành công
        this.statusLabel.setVisible(false);
    }

    @Override
    public void onError(String message) {

        this.statusLabel.setText("⚠️ Error: " + message);
        this.statusLabel.setVisible(true);

        // Tùy chọn: Có thể rung lắc (shake) giao diện ở đây nếu muốn
    }


}
