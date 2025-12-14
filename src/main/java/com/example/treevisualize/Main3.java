package com.example.treevisualize;

import com.example.treevisualize.Controller.AnimationController;
import com.example.treevisualize.Trees.*;
import com.example.treevisualize.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.Visualizer.TreeVisualizer;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Objects;

public class Main3 extends Application {

    // --- CÁC THÀNH PHẦN LOGIC ---
    private AnimationController controller;
    private TreeVisualizer visualizer;
    private Tree tree;

    // --- CÁC THÀNH PHẦN GIAO DIỆN ---
    private Canvas canvas;
    private PseudoCodeBlock pseudoCode;
    private TextField inputField;

    @Override
    public void start(Stage primaryStage) {
        // 1. KHỞI TẠO CANVAS (VÙNG VẼ)
        // Lưu ý: Phải khởi tạo Canvas trước khi đưa vào Pane
        canvas = new Canvas(800, 600);
        Pane canvasPane = new Pane(canvas);

        // Ràng buộc kích thước để Canvas tự co giãn theo Pane chứa nó
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());

        // Thêm CSS class cho pane chứa canvas (thay vì setStyle cứng)
        canvasPane.getStyleClass().add("canvas-pane");

        // 2. KHỞI TẠO BẢNG CODE (BÊN PHẢI)
        VBox codeContainer = new VBox();
        pseudoCode = new PseudoCodeBlock(codeContainer);
        VBox rightPane = new VBox(codeContainer);
        rightPane.setPrefWidth(320); // Rộng hơn một chút để dễ đọc
        rightPane.getStyleClass().add("code-pane"); // Thêm CSS class

        // 3. KHỞI TẠO THANH ĐIỀU KHIỂN (BÊN DƯỚI)
        HBox controls = createControls();

        // 4. KHỞI TẠO HỆ THỐNG MẶC ĐỊNH
        initializeSystem("Red Black Tree");

        // 5. BỐ CỤC CHÍNH (BorderPane)
        BorderPane root = new BorderPane();
        root.setCenter(canvasPane);
        root.setRight(rightPane);
        root.setBottom(controls);

        // 6. TẠO SCENE VÀ NẠP CSS
        Scene scene = new Scene(root, 1280, 800); // Kích thước cửa sổ: 1280x800

        // Nạp file style.css từ thư mục resources
        try {
            // Đảm bảo file style.css nằm ngay trong thư mục src/main/resources/
            String css = Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("Cảnh báo: Không tìm thấy file style.css! Giao diện sẽ hiển thị mặc định.");
            // e.printStackTrace(); // Bỏ comment nếu muốn xem chi tiết lỗi
        }

        primaryStage.setTitle("Tree Visualization App - Professional Edition");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Hàm khởi tạo lại hệ thống khi đổi loại cây
     */
    private void initializeSystem(String treeType) {
        // A. Chọn loại cây và cập nhật mã giả tương ứng
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

        // B. Tái tạo Visualizer và Controller
        // (Visualizer nhận canvas cũ để vẽ lên đó, Tree nhận logic mới)
        visualizer = new TreeVisualizer(tree, canvas);
        controller = new AnimationController(tree, visualizer, pseudoCode);

        // C. Vẽ trạng thái ban đầu (Cây rỗng)
        visualizer.render();
        System.out.println("Switched to: " + treeType);
    }

    /**
     * Tạo thanh công cụ điều khiển phía dưới
     */
    private HBox createControls() {
        HBox box = new HBox(15); // Khoảng cách giữa các phần tử là 15px
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("control-bar"); // CSS class cho thanh công cụ

        // --- NHÓM 1: CHỌN LOẠI CÂY ---
        Label lblType = new Label("Type:");
        ComboBox<String> cboTreeType = new ComboBox<>();
        cboTreeType.getItems().addAll("Red Black Tree", "Binary Search Tree", "Binary Tree (Normal)");
        cboTreeType.setValue("Red Black Tree");
        // Khi chọn xong thì reset hệ thống
        cboTreeType.setOnAction(e -> initializeSystem(cboTreeType.getValue()));

        Separator sep1 = new Separator(javafx.geometry.Orientation.VERTICAL);

        // --- NHÓM 2: THAO TÁC CƠ BẢN (Insert, Delete...) ---
        inputField = new TextField();
        inputField.setPromptText("Val...");
        inputField.setPrefWidth(70);

        Button btnInsert = new Button("Add");
        btnInsert.getStyleClass().add("btn-success"); // Class CSS nút xanh lá
        btnInsert.setOnAction(e -> handleInsert());

        Button btnDelete = new Button("Del");
        btnDelete.getStyleClass().add("btn-danger"); // Class CSS nút đỏ
        btnDelete.setOnAction(e -> handleDelete());

        Button btnRandom = new Button("Rnd");
        btnRandom.getStyleClass().add("button");
        btnRandom.setOnAction(e -> handleRandom());

        Button btnClear = new Button("Clr");
        btnClear.getStyleClass().add("button");
        btnClear.setOnAction(e -> initializeSystem(cboTreeType.getValue()));

        Separator sep2 = new Separator(javafx.geometry.Orientation.VERTICAL);

        // --- NHÓM 3: DUYỆT CÂY (TRAVERSAL) - Mới thêm vào ---
        ComboBox<String> cboTraversal = new ComboBox<>();
        cboTraversal.getItems().addAll(
                "In-Order (LNR)",
                "Pre-Order (NLR)",
                "Post-Order (LRN)",
                "BFS (Level Order)"
        );
        cboTraversal.setValue("In-Order (LNR)");
        cboTraversal.setPrefWidth(140);

        Button btnTraverse = new Button("Run");
        btnTraverse.getStyleClass().add("btn-primary"); // Class CSS nút xanh dương
        btnTraverse.setOnAction(e -> {
            if (controller != null) {
                // Gọi hàm startTraversal trong Controller (đã thêm ở bước trước)
                controller.startTraversal(cboTraversal.getValue());
            }
        });

        Separator sep3 = new Separator(javafx.geometry.Orientation.VERTICAL);

        // --- NHÓM 4: ĐIỀU KHIỂN ANIMATION (Play/Pause/Reset) ---
        Button btnPlay = new Button("▶");
        btnPlay.getStyleClass().add("button");
        btnPlay.setTooltip(new Tooltip("Play"));
        btnPlay.setOnAction(e -> controller.play());

        Button btnPause = new Button("⏸");
        btnPause.getStyleClass().add("button");
        btnPause.setTooltip(new Tooltip("Pause"));
        btnPause.setOnAction(e -> controller.pause());

        Button btnReset = new Button("⏮");
        btnReset.getStyleClass().add("button");
        btnReset.setTooltip(new Tooltip("Reset Step"));
        btnReset.setOnAction(e -> controller.reset());
        
        
        //btn Statistic
        Button btnStatistic = new Button("Stat");
        btnStatistic.getStyleClass().add("btn-info");
        btnStatistic.setTooltip(new Tooltip("Tree Statistics"));
        btnStatistic.setOnAction(e -> showStatistics());
        



        // Thêm tất cả vào HBox
        box.getChildren().addAll(
                lblType, cboTreeType, sep1,
                inputField, btnInsert, btnDelete, btnRandom, btnClear,
                sep2,
                cboTraversal, btnTraverse, // <-- Nút traversal nằm ở đây
                btnStatistic,  
                sep3,
                btnPlay, btnPause, btnReset
        );

        return box;
    }

    // --- CÁC HÀM XỬ LÝ SỰ KIỆN ---
    
    private void showStatistics() {
        if (tree == null) return;

        int height = tree.getHeight();
        int nodeCount = tree.getNodeCount();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tree Statistics");
        alert.setHeaderText("Current Tree Information");
        alert.setContentText(
                "Number of nodes: " + nodeCount +
                "\nTree height: " + height
        );
        alert.showAndWait();
    }


    private void handleInsert() {
        try {
            int val = Integer.parseInt(inputField.getText());
            controller.startInsert(val);
            inputField.clear();
            inputField.requestFocus();
        } catch (NumberFormatException e) {
            showAlert("Lỗi nhập liệu", "Vui lòng nhập số nguyên hợp lệ!");
        }
    }

    private void handleDelete() {
        try {
            int val = Integer.parseInt(inputField.getText());
            controller.startDelete(val);
            inputField.clear();
        } catch (NumberFormatException e) {
            showAlert("Lỗi nhập liệu", "Vui lòng nhập số nguyên hợp lệ!");
        }
    }

    private void handleRandom() {
        for (int i = 0; i < 5; i++) {
            int val = (int) (Math.random() * 100);
            controller.startInsert(val);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // --- CÁC HÀM CẬP NHẬT MÃ GIẢ (HELPERS) ---
    // Các hàm này dùng để thay đổi nội dung bảng bên phải theo loại cây

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