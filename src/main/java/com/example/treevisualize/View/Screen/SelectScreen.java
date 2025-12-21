package com.example.treevisualize.View.Screen;

import com.example.treevisualize.Model.Description.TreeType;
import com.example.treevisualize.demo.Main5;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import java.util.Objects;

public class SelectScreen {
    private final Main5 mainApp;

    public SelectScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("selection-pane");
        root.setPadding(new Insets(20));

        Button btnBack = new Button("⬅ Back");
        btnBack.getStyleClass().add("button");
        btnBack.setOnAction(e -> mainApp.switchToIntroScreen());

        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        Label lblHeader = new Label("Choose your tree");
        lblHeader.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label lblSub = new Label("Select a structure to visualize immediately");
        lblSub.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");
        titleBox.getChildren().addAll(lblHeader, lblSub);

        BorderPane topHeader = new BorderPane();
        topHeader.setLeft(btnBack);
        topHeader.setCenter(titleBox);
        BorderPane.setAlignment(titleBox, Pos.CENTER);
        topHeader.setPadding(new Insets(0, 0, 20, 0));

        root.setTop(topHeader);

        GridPane grid = createGrid();
        VBox gridContainer = new VBox(grid);
        gridContainer.setAlignment(Pos.CENTER);
        root.setCenter(gridContainer);
        mainApp.switchScene(root, 1200, 850);
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(33.33);
        grid.getColumnConstraints().addAll(colConst, colConst, colConst);

        int col = 0;
        int row = 0;

        // Duyệt qua Enum để tự động tạo Card
        for (TreeType type : TreeType.values()) {
            VBox card = createSelectableCard(type);
            grid.add(card, col, row);

            col++;
            if (col > 2) {
                col = 0;
                row++;
            }
        }
        // Thêm slot trống nếu dòng chưa đầy (để bố cục đẹp)
        while (col <= 2 && row < 2) { // Giả sử tối đa 2 dòng
            grid.add(createEmptySlot(), col, row);
            col++;
        }

        return grid;
    }

    private VBox createSelectableCard(TreeType type) {
        VBox card = new VBox(10);
        card.getStyleClass().add("tree-card");
        card.setAlignment(Pos.CENTER);
        card.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(card, true);
        GridPane.setFillHeight(card, true);

        // Load Icon từ Enum
        ImageView iconView = new ImageView();
        try {
            String path = (type.getIconPath() != null) ? type.getIconPath() : "/images/TreeView.png";
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            iconView.setImage(img);
            iconView.setFitHeight(50);
            iconView.setPreserveRatio(true);
        } catch (Exception e) { /* Ignored */ }

        Label lblTitle = new Label(type.getDisplayName());
        lblTitle.getStyleClass().add("card-title");
        lblTitle.setTextAlignment(TextAlignment.CENTER);
        lblTitle.setWrapText(true);

        // Mô tả ngắn có thể lấy từ Enum (nếu có) hoặc để trống
        Label lblDesc = new Label("Click to explore");
        lblDesc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");

        card.getChildren().addAll(iconView, lblTitle, lblDesc);

        // Sự kiện Click: Truyền Enum vào Main
        card.setOnMouseClicked(e -> {
            mainApp.setSelectedTreeType(type);
            mainApp.switchToInfoScreen();
        });
        card.setStyle("-fx-cursor: hand;");

        return card;
    }

    private VBox createEmptySlot() {
        VBox slot = new VBox();
        slot.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 15;");
        slot.setAlignment(Pos.CENTER);
        slot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        slot.getChildren().add(new Label("Coming Soon..."));
        return slot;
    }
}