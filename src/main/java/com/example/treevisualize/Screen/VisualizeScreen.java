package com.example.treevisualize.Screen;

import com.example.treevisualize.Controller.AnimationController;
import com.example.treevisualize.Description.TreeType;
import com.example.treevisualize.Main5;
import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.TraversalType;
import com.example.treevisualize.Trees.Tree;
import com.example.treevisualize.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.Visualizer.TreeVisualizer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Random;

public class VisualizeScreen {

    private final Main5 mainApp;

    // Các thành phần cốt lõi
    private Tree tree;
    private TreeVisualizer visualizer;
    private AnimationController controller;
    private PseudoCodeBlock pseudoCode;

    // UI Controls
    private TextField tfParentInput;
    private TextField tfInput;
    private ComboBox<TreeType> cboTreeType;
    private Slider sliderSpeed;
    private ComboBox<TraversalType> cboTraversal;
    private Button btnTraverse;

    // Biến lưu vị trí chuột để tính toán Pan (Kéo thả)
    private final double[] lastMouse = new double[2];

    public VisualizeScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("visualizer-pane");

        // --- 1. TOP BAR ---
        Button btnHome = new Button("Home");
        btnHome.getStyleClass().add("button");
        btnHome.setOnAction(e -> mainApp.switchToIntroScreen());
        Label lblTitle = new Label("Visualizer Mode");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        ToolBar topBar = new ToolBar(btnHome, new Separator(), lblTitle);
        root.setTop(topBar);

        // --- 2. CENTER (CANVAS + CAMERA CONTROLS) ---
        // Canvas kích thước cố định, đóng vai trò là "Viewport" (Cửa sổ nhìn)
        // Kích thước này đủ lớn để phủ màn hình laptop thông thường
        Canvas canvas = new Canvas(1200, 800);

        // Đặt trong Pane để layout dễ dàng hơn
        Pane canvasPane = new Pane(canvas);
        canvasPane.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7;");

        // [EVENT] Xử lý sự kiện KÉO THẢ (PAN) - Dịch chuyển Camera
        canvas.setOnMousePressed(event -> {
            lastMouse[0] = event.getX();
            lastMouse[1] = event.getY();
        });

        canvas.setOnMouseDragged(event -> {
            if (visualizer != null) {
                // Tính khoảng cách chuột đã di chuyển
                double deltaX = event.getX() - lastMouse[0];
                double deltaY = event.getY() - lastMouse[1];

                // Dịch chuyển Camera
                visualizer.pan(deltaX, deltaY);
            }
            // Cập nhật vị trí chuột cũ
            lastMouse[0] = event.getX();
            lastMouse[1] = event.getY();
        });

        // [EVENT] Xử lý sự kiện LĂN CHUỘT (ZOOM) - Phóng to/Thu nhỏ
        canvas.setOnScroll(event -> {
            if (visualizer != null) {
                double zoomFactor = 1.05; // Hệ số zoom mỗi lần lăn
                if (event.getDeltaY() < 0) {
                    zoomFactor = 1 / zoomFactor; // Thu nhỏ nếu lăn xuống
                }

                // Gọi hàm zoom trong Visualizer (cần đảm bảo bạn đã thêm hàm này vào TreeVisualizer)
                // Nếu chưa có hàm zoom, bạn có thể dùng tạm:
                // double newScale = visualizer.getScale() * zoomFactor; visualizer.setScale(newScale);
                visualizer.zoom(zoomFactor);
            }
            event.consume();
        });

        // --- 3. RIGHT (PSEUDO CODE) ---
        VBox rightPane = new VBox();
        rightPane.setMinWidth(0);
        rightPane.setPadding(new Insets(10));
        rightPane.setStyle("-fx-background-color: #f4f6f7;");

        Label lblCode = new Label("Pseudo Code");
        lblCode.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-font-size: 14px;");

        VBox codeContainer = new VBox(5);
        pseudoCode = new PseudoCodeBlock(codeContainer);
        rightPane.getChildren().addAll(lblCode, codeContainer);

        // --- 4. SPLIT PANE ---
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(canvasPane, rightPane); // Add Canvas trực tiếp, không qua ScrollPane
        splitPane.setDividerPositions(0.75);
        SplitPane.setResizableWithParent(rightPane, false);

        root.setCenter(splitPane);

        // --- 5. BOTTOM (CONTROLS) ---
        HBox controls = createControls(canvas);
        root.setBottom(controls);

        // [INIT] Khởi tạo hệ thống lần đầu
        initializeSystem(mainApp.getSelectedTreeType(), canvas);

        mainApp.switchScene(root, 1280, 800);
    }

    private HBox createControls(Canvas cv) {
        HBox box = new HBox(15);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1 0 0 0;");

        // Input controls
        tfParentInput = new TextField();
        tfParentInput.setPromptText("Parent");
        tfParentInput.setPrefWidth(60);
        // Mặc định ẩn, sẽ hiện nếu TreeType yêu cầu
        tfParentInput.setVisible(false);
        tfParentInput.setManaged(false);

        tfInput = new TextField();
        tfInput.setPromptText("Value...");
        tfInput.setPrefWidth(60);

        // Buttons
        Button btnInsert = new Button("Insert");
        btnInsert.getStyleClass().add("btn-primary");
        btnInsert.setOnAction(e -> handleInsert());

        Button btnDelete = new Button("Delete");
        btnDelete.getStyleClass().add("button");
        btnDelete.setStyle("-fx-text-fill: red;");
        btnDelete.setOnAction(e -> handleDelete());

        Button btnSearch = new Button("Search");
        btnSearch.setOnAction(e -> handleSearch());

        Button btnRandom = new Button("Rand(10)");
        btnRandom.setOnAction(e -> handleRandom());

        // Slider Speed
        Label lblSpeed = new Label("Speed:");
        sliderSpeed = new Slider(1, 5, 2);
        sliderSpeed.setShowTickMarks(true);
        sliderSpeed.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (controller != null) controller.setSpeed(newVal.doubleValue());
        });

        // ComboBox Tree Type
        Label lblType = new Label("Type:");
        cboTreeType = new ComboBox<>();
        cboTreeType.getItems().addAll(TreeType.values());

        // Hiển thị tên đẹp trong ComboBox
        cboTreeType.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(TreeType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDisplayName());
            }
        });
        cboTreeType.setButtonCell(cboTreeType.getCellFactory().call(null));

        cboTreeType.setValue(mainApp.getSelectedTreeType());
        cboTreeType.setOnAction(e -> {
            TreeType newType = cboTreeType.getValue();
            mainApp.setSelectedTreeType(newType);
            initializeSystem(newType, cv);
        });

        // Traversal Controls
        Separator sepTraversal = new Separator();
        sepTraversal.setOrientation(javafx.geometry.Orientation.VERTICAL);

        cboTraversal = new ComboBox<>();
        cboTraversal.getItems().addAll(TraversalType.values());
        cboTraversal.setValue(TraversalType.BFS);
        cboTraversal.setPrefWidth(100);

        btnTraverse = new Button("Traverse");
        btnTraverse.getStyleClass().add("button");
        btnTraverse.setOnAction(e -> handleTraverse());

        // Add all to box
        box.getChildren().addAll(
                tfParentInput, tfInput, btnInsert, btnDelete, btnSearch,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                btnRandom,
                sepTraversal, cboTraversal, btnTraverse,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                lblSpeed, sliderSpeed,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                lblType, cboTreeType
        );
        return box;
    }

    // [HÀM QUAN TRỌNG] Khởi tạo hệ thống Logic + Visualizer
    private void initializeSystem(TreeType type, Canvas cv) {
        // 1. Ẩn/Hiện ô nhập Parent tuỳ loại cây
        if (type.isRequiresParentInput()) {
            tfParentInput.setVisible(true);
            tfParentInput.setManaged(true);
        } else {
            tfParentInput.setVisible(false);
            tfParentInput.setManaged(false);
        }

        // 2. Tạo cây mới
        // Lưu ý: Đảm bảo Enum TreeType của bạn đã có phương thức createTreeInstance() hoặc dùng switch case
        // Ở đây giả định bạn đã cập nhật Enum như các bước trước
        this.tree = type.createTreeInstance();

        // 3. Hiển thị mã giả (Insert) mặc định
        PseudoCodeStrategy strategy = type.getInsertStrategy();
        if (strategy != null) {
            pseudoCode.setCode(strategy.getTitle(), strategy.getLines());
        }

        // 4. Khởi tạo Visualizer (Sử dụng CompactTreeLayout & Renderer tương ứng)
        visualizer = new TreeVisualizer(tree, type, cv);

        // 5. Kết nối Controller
        controller = new AnimationController(tree, visualizer, pseudoCode);
        controller.setSpeed(sliderSpeed != null ? sliderSpeed.getValue() : 2.0);

        // Vẽ lần đầu
        visualizer.render();
    }

    // --- CÁC HÀM XỬ LÝ SỰ KIỆN NÚT BẤM ---

    private void handleInsert() {
        TreeType currentType = mainApp.getSelectedTreeType();
        try {
            String valText = tfInput.getText().trim();
            if (valText.isEmpty()) return;

            int childValue = Integer.parseInt(valText);

            if (currentType.isRequiresParentInput()) {
                String parentText = tfParentInput.getText().trim();
                if (parentText.isEmpty()) {
                    // Nếu không nhập parent cho cây General -> Insert Root (nếu cây rỗng)
                    controller.startInsert(childValue);
                } else {
                    int parentValue = Integer.parseInt(parentText);
                    controller.startInsert(parentValue, childValue);
                }
            } else {
                controller.startInsert(childValue);
            }

            tfInput.clear();
            if (tfParentInput.isVisible()) tfParentInput.clear();
            tfInput.requestFocus();

        } catch (NumberFormatException e) {
            mainApp.showAlert("Input Error", "Please enter a valid integer number!");
        }
    }

    private void handleDelete() {
        try {
            String valText = tfInput.getText().trim();
            if (valText.isEmpty()) return;

            int value = Integer.parseInt(valText);
            controller.startDelete(value);
            tfInput.clear();
            tfInput.requestFocus();
        } catch (NumberFormatException e) {
            mainApp.showAlert("Input Error", "Invalid number!");
        }
    }

    private void handleSearch() {
        try {
            String valText = tfInput.getText().trim();
            if (valText.isEmpty()) return;

            int value = Integer.parseInt(valText);
            controller.startSearch(value);
            tfInput.clear();
            tfInput.requestFocus();
        } catch (NumberFormatException e) {
            mainApp.showAlert("Input Error", "Invalid number!");
        }
    }

    private void handleRandom() {
        Random rand = new Random();
        // Chèn 10 số ngẫu nhiên để test nhanh
        for (int i = 0; i < 10; i++) {
            // Với General Tree, random insert sẽ khó hơn vì cần Parent ID,
            // nên ở đây ta chỉ random đơn giản cho BST/AVL/RBT
            if (!cboTreeType.getValue().isRequiresParentInput()) {
                controller.startInsert(rand.nextInt(99) + 1);
            }
        }
        // Nếu là General Tree, bạn có thể viết logic random riêng (lấy random 1 node có sẵn làm cha)
    }

    private void handleTraverse() {
        TraversalType selectedType = cboTraversal.getValue();
        if (selectedType != null) {
            controller.startTraversal(selectedType);
        }
    }
}