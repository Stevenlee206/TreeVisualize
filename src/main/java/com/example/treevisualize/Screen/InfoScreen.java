package com.example.treevisualize.Screen;

import com.example.treevisualize.Description.DescriptionFactory;
import com.example.treevisualize.Description.Description;
import com.example.treevisualize.Main5;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class InfoScreen {

    private final Main5 mainApp;

    public InfoScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        // 1. Lấy loại cây đang chọn từ Main3
        String treeType = mainApp.getSelectedTreeType();

        BorderPane root = new BorderPane();
        root.getStyleClass().add("info-pane");

        // 2. Nội dung chính (Giữa màn hình)
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setMaxWidth(800); // Giới hạn chiều rộng để đọc cho dễ

        // Tiêu đề
        Label lblHeading = new Label(treeType);
        lblHeading.getStyleClass().add("info-heading");

        // Nội dung mô tả (Lấy từ Factory - Đa hình)
        Description strategy = DescriptionFactory.getStrategy(treeType);
        Text txtDesc = new Text(strategy.getDescription());
        txtDesc.getStyleClass().add("info-desc");
        txtDesc.setWrappingWidth(700); // Tự xuống dòng nếu quá dài

        // 3. Các nút bấm (Cuối màn hình)
        HBox actions = new HBox(20);

        // Nút Back: Quay lại màn hình chọn cây
        Button btnBack = new Button("⬅ Back");
        btnBack.getStyleClass().add("button");
        btnBack.setOnAction(e -> mainApp.switchToSelectScreen());

        // Nút Start: Vào màn hình Visualize chính
        Button btnStart = new Button("START VISUALIZE 🚀");
        btnStart.getStyleClass().add("btn-primary");
        btnStart.setStyle("-fx-font-size: 16px; -fx-padding: 10 25;");
        // Gọi hàm chuyển sang Visualizer
        btnStart.setOnAction(e -> mainApp.switchToVisualizerScreen());

        actions.getChildren().addAll(btnBack, btnStart);
        content.getChildren().addAll(lblHeading, txtDesc, new Separator(), actions);

        root.setCenter(content);

        // Gọi Main3 để hiển thị
        mainApp.switchScene(root, 1100, 750);
    }
}
