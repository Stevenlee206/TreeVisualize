package com.example.treevisualize;

import com.example.treevisualize.Controller.AnimationController;
import com.example.treevisualize.Trees.*;
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

public class Main2 extends Application {

    // Các thành phần cốt lõi
    private AnimationController controller;
    private TreeVisualizer visualizer;
    private Tree tree;

    // Các thành phần giao diện
    private Canvas canvas;
    private PseudoCodeBlock pseudoCode;
    private TextField inputField;

    @Override
    public void start(Stage primaryStage) {
        // --- 1. KHỞI TẠO CANVAS TRƯỚC (Quan trọng) ---
        // Phải new Canvas trước khi dùng nó
        canvas = new Canvas(800, 600);
        Pane canvasPane = new Pane(canvas);

        // Ràng buộc kích thước để Canvas tự co giãn theo Pane chứa nó
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());
        canvasPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc;");

        // --- 2. KHỞI TẠO BẢNG CODE ---
        VBox codeContainer = new VBox();
        pseudoCode = new PseudoCodeBlock(codeContainer);
        VBox rightPane = new VBox(codeContainer);
        rightPane.setPrefWidth(300); // Tăng độ rộng bảng code lên chút cho đẹp
        rightPane.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc;");

        // --- 3. KHỞI TẠO THANH ĐIỀU KHIỂN ---
        HBox controls = createControls();

        // --- 4. KHỞI TẠO HỆ THỐNG LOGIC ---
        initializeSystem("Red Black Tree");

        // --- 5. TẠO LAYOUT TỔNG (ROOT) ---
        BorderPane root = new BorderPane();
        root.setCenter(canvasPane);
        root.setRight(rightPane);
        root.setBottom(controls);

        // --- 6. TẠO SCENE VỚI KÍCH THƯỚC MONG MUỐN ---
        // Tham số 1200, 800 nằm ở đây mới đúng
        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setTitle("Tree Visualization App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeSystem(String treeType) {
        switch (treeType) {
            case "Binary Search Tree":
                tree = new BinarySearchTree();
                updatePseudoCodeForBST();
                break;
            case "Binary Tree (Normal)":
                tree = new BinaryTree();
                updatePseudoCodeForBinaryTree();
                break;
            case "Red Black Tree":
            default:
                tree = new RedBlackTree();
                updatePseudoCodeForRBT();
                break;
        }

        // Tái tạo Visualizer và Controller
        visualizer = new TreeVisualizer(tree, canvas);
        controller = new AnimationController(tree, visualizer, pseudoCode);
        visualizer.render();
        System.out.println("Switched to: " + treeType);
    }

    private HBox createControls() {
        HBox box = new HBox(10);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #e0e0e0;");

        // --- 1. LOẠI CÂY ---
        Label lblType = new Label("Type:");
        ComboBox<String> cboTreeType = new ComboBox<>();
        cboTreeType.getItems().addAll("Red Black Tree", "Binary Search Tree", "Binary Tree (Normal)");
        cboTreeType.setValue("Red Black Tree");
        cboTreeType.setStyle("-fx-font-weight: bold;");
        cboTreeType.setOnAction(e -> initializeSystem(cboTreeType.getValue()));

        Separator sep1 = new Separator(javafx.geometry.Orientation.VERTICAL);

        // --- 2. THAO TÁC ---
        inputField = new TextField();
        inputField.setPromptText("Val...");
        inputField.setPrefWidth(60);

        Button btnInsert = new Button("Add");
        btnInsert.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnInsert.setOnAction(e -> handleInsert());

        Button btnDelete = new Button("Del");
        btnDelete.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        btnDelete.setOnAction(e -> handleDelete());

        Button btnRandom = new Button("Rnd");
        btnRandom.setOnAction(e -> handleRandom());

        Button btnClear = new Button("Clr");
        btnClear.setOnAction(e -> initializeSystem(cboTreeType.getValue()));

        Separator sep2 = new Separator(javafx.geometry.Orientation.VERTICAL);

        // --- 3. TRAVERSAL ---
        ComboBox<String> cboTraversal = new ComboBox<>();
        cboTraversal.getItems().addAll(
                "In-Order (LNR)",
                "Pre-Order (NLR)",
                "Post-Order (LRN)",
                "BFS (Level Order)"
        );
        cboTraversal.setValue("In-Order (LNR)");
        cboTraversal.setPrefWidth(120);

        Button btnTraverse = new Button("Run");
        btnTraverse.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnTraverse.setOnAction(e -> {
            if (controller != null) {
                controller.startTraversal(cboTraversal.getValue());
            }
        });

        Separator sep3 = new Separator(javafx.geometry.Orientation.VERTICAL);

        // --- 4. ANIMATION CONTROL ---
        Button btnPlay = new Button("▶");
        btnPlay.setOnAction(e -> controller.play());

        Button btnPause = new Button("⏸");
        btnPause.setOnAction(e -> controller.pause());

        Button btnReset = new Button("⏮");
        btnReset.setOnAction(e -> controller.reset());

        box.getChildren().addAll(
                lblType, cboTreeType, sep1,
                inputField, btnInsert, btnDelete, btnRandom, btnClear,
                sep2,
                cboTraversal, btnTraverse,
                sep3,
                btnPlay, btnPause, btnReset
        );

        return box;
    }

    private void handleInsert() {
        try {
            int val = Integer.parseInt(inputField.getText());
            controller.startInsert(val);
            inputField.clear();
            inputField.requestFocus();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid integer!");
        }
    }

    private void handleDelete() {
        try {
            int val = Integer.parseInt(inputField.getText());
            controller.startDelete(val);
            inputField.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid integer!");
        }
    }

    private void handleRandom() {
        for(int i=0; i<5; i++) {
            controller.startInsert((int)(Math.random() * 100));
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updatePseudoCodeForRBT() {
        pseudoCode.setCode(Arrays.asList(
                "RED-BLACK TREE INSERT:",
                "1. BST Insert (Color = RED)",
                "2. While (Parent is RED):",
                "   3. Check Uncle color",
                "   4. Rotate / Recolor",
                "5. Set Root to BLACK"
        ));
    }

    private void updatePseudoCodeForBST() {
        pseudoCode.setCode(Arrays.asList(
                "BINARY SEARCH TREE INSERT:",
                "1. If root is null, create root",
                "2. Compare val with current node",
                "3. If val < current, go Left",
                "4. If val > current, go Right",
                "5. Link new node"
        ));
    }

    private void updatePseudoCodeForBinaryTree() {
        pseudoCode.setCode(Arrays.asList(
                "BINARY TREE (LEVEL ORDER) INSERT:",
                "1. Create new node",
                "2. Use Queue for BFS",
                "3. Find first node with missing child",
                "4. Insert Left if empty",
                "5. Else Insert Right"
        ));
    }

    public static void main(String[] args) {
        launch(args);
    }
}