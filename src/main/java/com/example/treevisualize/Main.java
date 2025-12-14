package com.example.treevisualize;

import com.example.treevisualize.Controller.AnimationController;
import com.example.treevisualize.Trees.RedBlackTree;
import com.example.treevisualize.Trees.Tree;
import com.example.treevisualize.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.Visualizer.TreeVisualizer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {

    private AnimationController controller;
    private TextField inputField;

    @Override
    public void start(Stage primaryStage) {
        // --- 1. KHỞI TẠO MODEL & VIEW ---

        // Chọn loại cây bạn muốn test (RedBlackTree hoặc BinarySearchTree)
        Tree tree = new RedBlackTree();

        // Canvas để vẽ
        Canvas canvas = new Canvas(800, 600);

        // com.example.treevisualize.Visualizer (View chính)
        TreeVisualizer visualizer = new TreeVisualizer(tree, canvas);

        // PseudoCode (View phụ)
        VBox codeContainer = new VBox();
        PseudoCodeBlock pseudoCode = new PseudoCodeBlock(codeContainer);
        // Nạp code giả lập để test hiển thị
        pseudoCode.setCode(Arrays.asList(
                "1. Insert node into BST",
                "2. Color node RED",
                "3. Fix Double Red violation",
                "4. Rotate/Recolor if needed"
        ));

        // com.example.treevisualize.Controller (Đạo diễn)
        controller = new AnimationController(tree, visualizer, pseudoCode);

        // --- 2. XÂY DỰNG GIAO DIỆN (LAYOUT) ---

        // A. Phần trung tâm: Chứa Canvas (đặt trong Pane để resize tự động)
        Pane canvasPane = new Pane(canvas);
        // Bind kích thước canvas theo pane cha
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());
        canvasPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc;");

        // B. Phần bên phải: Chứa bảng code giả
        VBox rightPane = new VBox(codeContainer);
        rightPane.setPrefWidth(250);
        rightPane.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc;");

        // C. Phần bên dưới: Các nút điều khiển
        HBox controls = createControls();

        // D. Layout chính (BorderPane)
        BorderPane root = new BorderPane();
        root.setCenter(canvasPane);
        root.setRight(rightPane);
        root.setBottom(controls);

        // --- 3. HIỂN THỊ CỬA SỔ ---
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setTitle("Tree Visualization - Red Black Tree Demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Vẽ lại lần đầu (cây rỗng)
        visualizer.render();
    }

    /**
     * Tạo thanh công cụ phía dưới (Input, Buttons)
     */
    private HBox createControls() {
        HBox box = new HBox(10);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #e0e0e0;");

        // Input nhập số
        inputField = new TextField();
        inputField.setPromptText("Enter number...");
        inputField.setPrefWidth(100);

        // Nút Insert
        Button btnInsert = new Button("Insert");
        btnInsert.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnInsert.setOnAction(e -> handleInsert());

        // Nút Delete
        Button btnDelete = new Button("Delete");
        btnDelete.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        btnDelete.setOnAction(e -> handleDelete());

        // Nút Random (Tạo cây ngẫu nhiên cho nhanh)
        Button btnRandom = new Button("Random 5 Nodes");
        btnRandom.setOnAction(e -> {
            for (int i = 0; i < 5; i++) {
                int val = (int) (Math.random() * 100);
                controller.startInsert(val);
            }
        });

        // Thanh phân cách
        Separator separator = new Separator();
        separator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        // Nút điều khiển Animation
        Button btnPlay = new Button("▶ Play");
        btnPlay.setOnAction(e -> controller.play());

        Button btnPause = new Button("⏸ Pause");
        btnPause.setOnAction(e -> controller.pause());

        Button btnReset = new Button("⏮ Reset");
        btnReset.setOnAction(e -> controller.reset());

        box.getChildren().addAll(
                new Label("Value:"), inputField,
                btnInsert, btnDelete, btnRandom,
                separator,
                btnPlay, btnPause, btnReset
        );

        return box;
    }

    // --- LOGIC XỬ LÝ SỰ KIỆN ---

    private void handleInsert() {
        try {
            int val = Integer.parseInt(inputField.getText());
            // Gọi com.example.treevisualize.Controller để bắt đầu quy trình: Ghi hình -> Insert -> Play
            controller.startInsert(val);
            inputField.clear();
            inputField.requestFocus();
        } catch (NumberFormatException e) {
            showAlert("Lỗi nhập liệu", "Vui lòng nhập một số nguyên hợp lệ!");
        }
    }

    private void handleDelete() {
        try {
            int val = Integer.parseInt(inputField.getText());
            controller.startDelete(val);
            inputField.clear();
        } catch (NumberFormatException e) {
            showAlert("Lỗi nhập liệu", "Vui lòng nhập một số nguyên hợp lệ!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}