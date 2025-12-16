package com.example.treevisualize.Screen;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import com.example.treevisualize.Main5;

import java.util.Objects;

public class IntroScreen {
    // 1. Khai báo tham chiếu đến Main5
    private final Main5 mainApp;

    // 2. Constructor nhận Main5 vào
    public IntroScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }
    public void show() {
        // LAYER 1: CONTAINER GỐC (StackPane giúp xếp chồng các lớp lên nhau)
        StackPane root = new StackPane();
        root.getStyleClass().add("intro-pane");

        // LAYER 2: LỚP TRANG TRÍ (Chứa các hình ảnh bay lượn xung quanh)
        // Dùng Group hoặc Pane để chứa các vật thể trang trí mà không ảnh hưởng bố cục chính
        Pane decorationLayer = new Pane();
        // Để chuột có thể bấm xuyên qua lớp này vào nút Start bên dưới (quan trọng)
        decorationLayer.setPickOnBounds(false);

        // --- KHU VỰC THÊM ẢNH TÙY Ý (DỄ MỞ RỘNG) ---
        // Cú pháp: addDecoration(container, tên_ảnh, độ_rộng, toạ_độ_X, toạ_độ_Y)
        // Tọa độ (0, 0) là chính giữa màn hình.
        // X > 0: Sang phải, X < 0: Sang trái
        // Y > 0: Xuống dưới, Y < 0: Lên trên
        addDecoration(root, "/images/TreeView.png", 195, -300, -181);
        addDecoration(root, "/images/TreeLogo.png", 195, 300, 140);
        addDecoration(root, "/images/BFS.png", 230, -340, 190);
        addDecoration(root, "/images/DFS.png", 230, -100, 190);

        // LAYER 3: LỚP GIAO DIỆN CHÍNH (Tiêu đề & Nút bấm)
        VBox uiLayer = new VBox(20);
        uiLayer.setAlignment(Pos.CENTER);
        uiLayer.setTranslateY(-105);
        // Cho phép chuột bấm xuyên qua các khoảng trống của VBox
        uiLayer.setPickOnBounds(false);

        // Tiêu đề (Đẩy xuống một chút để không đè lên Logo ở trên)
        Label title = new Label("Tree Data Structure\nVisualization");
        title.getStyleClass().add("intro-title");
        title.setTextAlignment(TextAlignment.CENTER);

        Label subtitle = new Label("Explore how Tree data structure work in an intuitive and vivid way.");
        subtitle.getStyleClass().add("intro-subtitle");
        subtitle.setTranslateY(30);

        Button btnNext = new Button("Start  ➜");
        btnNext.getStyleClass().add("btn-intro-next");
        btnNext.setOnAction(e -> mainApp.switchToSelectScreen());
        btnNext.setTranslateY(50);

        uiLayer.getChildren().addAll(title, subtitle, btnNext);
        root.getChildren().add(uiLayer);

        // [QUAN TRỌNG] Gọi hàm của Main3 để hiển thị Scene này
        mainApp.switchScene(root, 1000, 700);
    }

    /**
     * HÀM HELPER SIÊU MẠNH MẼ: Giúp thêm ảnh vào vị trí bất kỳ
     * @param root Container gốc (StackPane)
     * @param path Đường dẫn ảnh trong resources
     * @param width Độ rộng mong muốn
     * @param x Vị trí X so với tâm (Âm: Trái, Dương: Phải)
     * @param y Vị trí Y so với tâm (Âm: Trên, Dương: Dưới)
     */
    private void addDecoration(StackPane root, String path, double width, double x, double y) {
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            ImageView view = new ImageView(img);
            view.setFitHeight(width); // Dùng chiều cao hoặc rộng tuỳ ý
            view.setPreserveRatio(true);
            view.getStyleClass().add("intro-image");

            // Đặt vị trí tuyệt đối so với tâm
            view.setTranslateX(x);
            view.setTranslateY(y);

            // Thêm vào vị trí index 0 (nằm dưới cùng, sát nền) để không che chữ
            root.getChildren().add(0, view);

        } catch (Exception e) {
            System.err.println("Image do not found: " + path);
        }
    }

}