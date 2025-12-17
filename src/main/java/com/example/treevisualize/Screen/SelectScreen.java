package com.example.treevisualize.Screen;

import com.example.treevisualize.Main5;
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
        // Dùng BorderPane để chia bố cục: Trên (Top), Giữa (Center)
        BorderPane root = new BorderPane();
        root.getStyleClass().add("selection-pane");
        root.setPadding(new Insets(20));

        // --- PHẦN TOP: Nút Back + Tiêu đề ---
        // 1. Nút Back
        Button btnBack = new Button("⬅ Back");
        btnBack.getStyleClass().add("button");
        btnBack.setOnAction(e -> mainApp.switchToIntroScreen());

        // 2. Tiêu đề
        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        Label lblHeader = new Label("Choose your tree");
        lblHeader.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        Label lblSub = new Label("Select a structure to visualize immediately");
        lblSub.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");
        titleBox.getChildren().addAll(lblHeader, lblSub);

        // 3. Gom lại: Dùng BorderPane nhỏ cho phần Top để nút Back dính trái, Tiêu đề ở giữa
        BorderPane topHeader = new BorderPane();
        topHeader.setLeft(btnBack);      // Nút Back bên trái
        topHeader.setCenter(titleBox);   // Tiêu đề ở giữa
        // Chỉnh margin để tiêu đề không bị lệch do nút Back chiếm chỗ
        BorderPane.setAlignment(titleBox, Pos.CENTER);
        // Trick nhỏ: dịch tiêu đề sang trái 1 chút để bù lại khoảng trống của nút Back (tuỳ chọn)
        topHeader.setPadding(new Insets(0, 0, 20, 0));

        root.setTop(topHeader);

        // --- PHẦN CENTER: Lưới Card ---
        GridPane grid = createGrid();

        // Căn giữa Grid trong màn hình
        VBox gridContainer = new VBox(grid);
        gridContainer.setAlignment(Pos.CENTER);

        root.setCenter(gridContainer);

        // Hiển thị ra màn hình
        mainApp.switchScene(root, 1200, 850);
    }
    // --- CÁC HÀM HỖ TRỢ (HELPER METHODS) ---

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);

        // Chia cột đều nhau (33.33%)
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(33.33);
        grid.getColumnConstraints().addAll(colConst, colConst, colConst);

        // Chia hàng đều nhau
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(33.33);
        grid.getRowConstraints().addAll(rowConst, rowConst, rowConst);

        grid.setMaxSize(1000, 600);

        // Thêm các Card (Bạn có thể thay icon null bằng đường dẫn ảnh cụ thể)
        grid.add(createSelectableCard("Red Black Tree", "Self-Balancing Binary Tree.", "/images/RBT_icon.png"), 0, 0);
        grid.add(createSelectableCard("Binary Search Tree", "Standard BST Structure.", "/images/BST_icon.png"), 1, 0);
        grid.add(createSelectableCard("Binary Tree (Normal)", "Basic Level-Order Tree.", "/images/BT_icon.png"), 2, 0);
        grid.add(createSelectableCard("General Tree", "N-ary Tree (Multiple children).", "/images/GT_icon.png"), 0, 1);
        grid.add(createSelectableCard("AVL Tree", "Strictly Self-Balancing BST.", "/images/AVL_icon.png"), 1, 1);
        grid.add(createSelectableCard("Scapegoat Tree", "Weight-balanced BST (No extra storage).", "/images/SGT_icon.png"), 2, 1);

        grid.add(createEmptySlot(), 0, 2);
        grid.add(createEmptySlot(), 1, 2);
        grid.add(createEmptySlot(), 2, 2);

        return grid;
    }

    private VBox createSelectableCard(String title, String desc, String iconPath) {
        VBox card = new VBox(10);
        card.getStyleClass().add("tree-card");
        card.setAlignment(Pos.CENTER);

        // Dùng UserData để lưu tên cây vào chính cái Card đó (tiện cho việc tìm kiếm)
        card.setUserData(title);

        // Giãn full ô lưới
        card.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setFillWidth(card, true);
        GridPane.setFillHeight(card, true);

        // Icon
        ImageView iconView = new ImageView();
        try {
            // Nếu iconPath null hoặc lỗi thì dùng ảnh mặc định
            String path = (iconPath != null) ? iconPath : "/images/TreeView.png";
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            iconView.setImage(img);
            iconView.setFitHeight(50);
            iconView.setPreserveRatio(true);
        } catch (Exception e) {
            // Không làm gì nếu lỗi ảnh
        }

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("card-title");
        lblTitle.setTextAlignment(TextAlignment.CENTER);
        lblTitle.setWrapText(true);

        Label lblDesc = new Label(desc);
        lblDesc.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");
        lblDesc.setTextAlignment(TextAlignment.CENTER);
        lblDesc.setWrapText(true);

        card.getChildren().addAll(iconView, lblTitle, lblDesc);

        // SỰ KIỆN CLICK VÀO CARD
        card.setOnMouseClicked(e -> {
            // 1. Lưu loại cây vào Main5
            mainApp.setSelectedTreeType(title);
            // 2. Chuyển sang InfoScreen NGAY LẬP TỨC
            mainApp.switchToInfoScreen();
        });

        // Thêm hiệu ứng chuột để biết là bấm được
        card.setStyle("-fx-cursor: hand;");

        // Xóa dòng: cardList.add(card); -> Vì không cần quản lý list nữa
        return card;
    }

    private VBox createEmptySlot() {
        VBox slot = new VBox();
        slot.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 2; -fx-border-style: dashed; -fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 15; -fx-border-radius: 15;");
        slot.setAlignment(Pos.CENTER);
        slot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label lbl = new Label("Coming Soon");
        lbl.setStyle("-fx-text-fill: #95a5a6; -fx-font-style: italic;");
        slot.getChildren().add(lbl);

        return slot;
    }
}