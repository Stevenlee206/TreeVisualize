package com.example.treevisualize.Screen;

import com.example.treevisualize.Controller.AnimationController;
import com.example.treevisualize.Main5;
import com.example.treevisualize.PseudoCodeStore.PseudoCodeFactory;
import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Trees.*;
import com.example.treevisualize.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.Visualizer.TreeVisualizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.Random;

public class VisualizeScreen {

    private final Main5 mainApp;

    // Các thành phần cốt lõi
    private Tree tree;
    private TreeVisualizer visualizer;
    private AnimationController controller;
    private PseudoCodeBlock pseudoCode;
    private TextField tfParentInput;

    // UI Controls cần truy cập toàn cục trong class
    private TextField tfInput;
    private ComboBox<String> cboTreeType;
    private Slider sliderSpeed;

    public VisualizeScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("visualizer-pane"); // CSS nền nếu cần

        // ==========================================================
        // 1. TOP BAR (Nút Home & Tiêu đề)
        // ==========================================================
        Button btnHome = new Button("Home");
        btnHome.getStyleClass().add("button");
        btnHome.setOnAction(e -> mainApp.switchToIntroScreen());

        Label lblTitle = new Label("Visualizer Mode");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ToolBar topBar = new ToolBar(btnHome, new Separator(), lblTitle);
        root.setTop(topBar);

        // ==========================================================
        // 2. CENTER (Màn hình vẽ - Canvas)
        // ==========================================================
        // Pane bao ngoài để Canvas có thể resize theo cửa sổ
     // ================= CENTER (Canvas + Scroll) =================
        Canvas canvas = new Canvas(1200, 1000); 

        StackPane canvasWrapper = new StackPane(canvas);
        canvasWrapper.setStyle("-fx-background-color: white;");

        ScrollPane scrollPane = new ScrollPane(canvasWrapper);
        scrollPane.setPannable(true); 
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setCenter(scrollPane);


        // ==========================================================
        // 3. RIGHT (Mã giả - Pseudo Code)
        // ==========================================================
        VBox rightPane = new VBox();
        rightPane.setPrefWidth(350);
        rightPane.setPadding(new Insets(10));
        rightPane.setStyle("-fx-background-color: #f4f6f7; -fx-border-color: #bdc3c7; -fx-border-width: 0 0 0 1;");

        Label lblCode = new Label("Pseudo Code");
        lblCode.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-font-size: 14px;");

        // Container chứa các dòng code
        VBox codeContainer = new VBox(5);
        pseudoCode = new PseudoCodeBlock(codeContainer);

        rightPane.getChildren().addAll(lblCode, codeContainer);
        root.setRight(rightPane);

        // ==========================================================
        // 4. BOTTOM (Thanh điều khiển)
        // ==========================================================
        HBox controls = createControls(canvas);
        root.setBottom(controls);

        // ==========================================================
        // 5. KHỞI TẠO HỆ THỐNG
        // ==========================================================
        // Lấy loại cây đã chọn từ Main3 để khởi tạo lần đầu
        initializeSystem(mainApp.getSelectedTreeType(), canvas);

        mainApp.switchScene(root, 1280, 800);
    }

    /**
     * Hàm tạo thanh điều khiển bên dưới
     */
    private HBox createControls(Canvas cv) {
        HBox box = new HBox(15);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1 0 0 0;");

        // Input
        tfInput = new TextField();
        tfInput.setPromptText("Enter an int ...");
        tfInput.setPrefWidth(80);

        tfParentInput = new TextField();
        tfParentInput.setPromptText("Parent");
        tfParentInput.setPrefWidth(70);
        tfParentInput.setVisible(false); // Mặc định ẩn đi (vì mặc định là RBT)
        tfParentInput.setManaged(false);

        // Các nút chức năng
        Button btnInsert = new Button("Insert");
        btnInsert.getStyleClass().add("btn-primary"); // Style xanh
        btnInsert.setOnAction(e -> handleInsert());

        Button btnDelete = new Button("Delete");
        btnDelete.getStyleClass().add("button");
        btnDelete.setStyle("-fx-text-fill: red;"); // Chữ đỏ cảnh báo
        btnDelete.setOnAction(e -> handleDelete());

        Button btnSearch = new Button("Search");
        btnSearch.getStyleClass().add("button");
        btnSearch.setOnAction(e -> handleSearch());

        Button btnRandom = new Button("Random (10)");
        btnRandom.getStyleClass().add("button");
        btnRandom.setOnAction(e -> handleRandom());

        // Bổ sung Traverse button
        Button btnTraverse=new Button("Traverse");
        btnSearch.getStyleClass().add("button");
        // Nhớ thêm code xử lý Traverse vào đây


        // Slider tốc độ
        Label lblSpeed = new Label("Speed:");
        sliderSpeed = new Slider(1, 5, 2); // Min 1, Max 5, Default 2
        sliderSpeed.setShowTickMarks(true);
        sliderSpeed.setShowTickLabels(true);
        // Listener thay đổi tốc độ animation realtime
        sliderSpeed.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (controller != null) controller.setSpeed(newVal.doubleValue());
        });

        // ComboBox đổi loại cây nhanh
        Label lblType = new Label("Type:");
        cboTreeType = new ComboBox<>();
        cboTreeType.getItems().addAll(
                "Red Black Tree",
                "Binary Search Tree",
                "Binary Tree (Normal)",
                "General Tree",
                "AVL Tree",
                "Scapegoat Tree"
        );
        cboTreeType.setValue(mainApp.getSelectedTreeType());
        // Khi chọn loại mới -> Reset lại toàn bộ hệ thống
        cboTreeType.setOnAction(e -> {
            String newType = cboTreeType.getValue();
            mainApp.setSelectedTreeType(newType); // Cập nhật Main3
            initializeSystem(newType, cv);
        });

        box.getChildren().addAll(tfParentInput,
                tfInput, btnInsert, btnDelete, btnSearch, new Separator(),
                btnRandom, new Separator(),
                lblSpeed, sliderSpeed, new Separator(),
                lblType, cboTreeType
        );

        return box;
    }

    /**
     * Hàm quan trọng nhất: Khởi tạo Logic + Visualizer + Controller
     */
    private void initializeSystem(String treeType, Canvas cv) {
        // 1. Tạo Cây Logic (Model)
        if ("General Tree".equals(treeType)) {
            tfParentInput.setVisible(true);
            tfParentInput.setManaged(true);
        } else {
            tfParentInput.setVisible(false);
            tfParentInput.setManaged(false);
        }
        switch (treeType) {
            case "Binary Search Tree":
                tree = new BinarySearchTree();
                break;
            case "Binary Tree (Normal)":
                tree = new BinaryTree();
                break;
            case "General Tree":
                tree = new GeneralTree();
                break;
            case "AVL Tree":
                tree = new AVLTree();
                break;
            case "Scapegoat Tree":
                tree = new ScapegoatTree();
                break;
            case "Red Black Tree":
            default:
                tree = new RedBlackTree();
                break;
        }

        // 2. Cập nhật Mã giả (View Text)
        PseudoCodeStrategy strategy = PseudoCodeFactory.getInsertStrategy(treeType);
        pseudoCode.setCode(strategy.getLines());

        // 3. Khởi tạo Visualizer (View Graphics)
        visualizer = new TreeVisualizer(tree, cv);

        // 4. Khởi tạo Controller (Điều khiển)
        controller = new AnimationController(tree, visualizer, pseudoCode);

        // Cập nhật tốc độ từ slider hiện tại
        controller.setSpeed(sliderSpeed != null ? sliderSpeed.getValue() : 2.0);

        // Vẽ trạng thái ban đầu (cây rỗng)
        visualizer.render();
    }

    // --- CÁC HÀM XỬ LÝ SỰ KIỆN NÚT BẤM ---

    private void handleInsert() {
    String currentType = mainApp.getSelectedTreeType();

        try {
            // Lấy giá trị con (Child)
            String input = tfInput.getText().trim();
            if (input.isEmpty()) return;
            int childValue = Integer.parseInt(input);

            // ĐA HÌNH: Tự động cập nhật bảng mã giả dựa trên loại cây hiện tại
            var strategy = PseudoCodeFactory.getInsertStrategy(currentType);
            pseudoCode.setCode(strategy.getTitle(), strategy.getLines());

            if ("General Tree".equals(currentType)) {
                String parentText = tfParentInput.getText().trim();

                if (parentText.isEmpty()) {
                    // TRƯỜNG HỢP 1: Tạo Root hoặc chèn tự động nếu root đã có
                    controller.startInsert(childValue);
                } else {
                    // TRƯỜNG HỢP 2: Thêm node con vào node cha cụ thể
                    int parentValue = Integer.parseInt(parentText);
                    controller.startInsert(parentValue, childValue);
                }
            } else {
                // Các loại cây nhị phân: BST, RBT, AVL, Scapegoat...
                controller.startInsert(childValue);
            }

            // Dọn dẹp giao diện
            tfInput.clear();
            if (tfParentInput != null && tfParentInput.isVisible()) tfParentInput.clear();
            tfInput.requestFocus();

        } catch (NumberFormatException e) {
            mainApp.showAlert("Lỗi nhập liệu", "Vui lòng nhập số hợp lệ!");
        }
    }

    private void handleDelete() {
        try {
            String input = tfInput.getText().trim();
            if (input.isEmpty()) return;
            int value = Integer.parseInt(input);    

            // ĐA HÌNH: Cập nhật mã giả Xóa tương ứng với loại cây
            var delStrategy = PseudoCodeFactory.getDeleteStrategy(mainApp.getSelectedTreeType());
            pseudoCode.setCode(delStrategy.getTitle(), delStrategy.getLines());

            // Gọi controller xử lý xóa và chạy animation
            controller.startDelete(value);
            
            tfInput.clear();
            tfInput.requestFocus();
        } catch (NumberFormatException e) {
            mainApp.showAlert("Lỗi nhập liệu", "Vui lòng nhập số cần xóa!");
        }
    }

    private void handleSearch() {
        try {
            int value = Integer.parseInt(tfInput.getText().trim());
            controller.startSearch(value);
            tfInput.clear();
        } catch (NumberFormatException e) {
            mainApp.showAlert("Lỗi nhập liệu", "Vui lòng nhập số cần tìm!");
        }
    }

    private void handleRandom() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int val = rand.nextInt(99) + 1;
            controller.startInsert(val);
        }
    }

    private void handleTraverse() {

    }
}