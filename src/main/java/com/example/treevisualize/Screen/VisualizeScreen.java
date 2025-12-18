package com.example.treevisualize.Screen;

import com.example.treevisualize.Controller.AnimationController;
import com.example.treevisualize.Description.TreeType;
import com.example.treevisualize.Main5;
import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.TraversalType;
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

    // UI Controls
    private TextField tfParentInput;
    private TextField tfInput;
    private ComboBox<TreeType> cboTreeType; // Dùng Enum cho ComboBox
    private Slider sliderSpeed;
    private ComboBox<TraversalType> cboTraversal; // Dùng Enum TraversalType
    private Button btnTraverse;

    public VisualizeScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("visualizer-pane");

        // 1. TOP BAR
        Button btnHome = new Button("Home");
        btnHome.getStyleClass().add("button");
        btnHome.setOnAction(e -> mainApp.switchToIntroScreen());
        Label lblTitle = new Label("Visualizer Mode");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        ToolBar topBar = new ToolBar(btnHome, new Separator(), lblTitle);
        root.setTop(topBar);

        // 2. CENTER (CANVAS + ZOOM LOGIC)
        Canvas canvas = new Canvas(1200, 1000);

        // StackPane để căn giữa và nhận sự kiện zoom
        StackPane canvasWrapper = new StackPane(canvas);
        canvasWrapper.setStyle("-fx-background-color: white;");

        // Group: "Chìa khóa" để ScrollPane nhận diện kích thước thật khi Zoom
        javafx.scene.Group zoomGroup = new javafx.scene.Group(canvasWrapper);

        ScrollPane scrollPane = new ScrollPane(zoomGroup);
        scrollPane.setPannable(true); // Cho phép kéo chuột để di chuyển (Pan)
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // --- XỬ LÝ ZOOM ---

        // Cách 1: Zoom bằng Chuột (Giữ Ctrl + Lăn chuột)
        canvasWrapper.setOnScroll(event -> {
            if (event.isControlDown()) {
                event.consume();
                double zoomFactor = 1.1;
                double deltaY = event.getDeltaY();

                if (deltaY < 0) {
                    zoomFactor = 1 / zoomFactor;
                }

                double newScaleX = canvasWrapper.getScaleX() * zoomFactor;
                double newScaleY = canvasWrapper.getScaleY() * zoomFactor;

                // Giới hạn zoom từ 0.5x đến 5.0x
                if (newScaleX >= 0.5 && newScaleX <= 5.0) {
                    canvasWrapper.setScaleX(newScaleX);
                    canvasWrapper.setScaleY(newScaleY);
                }
            }
        });

        // Cách 2: Zoom bằng Trackpad (Ngón tay)
        canvasWrapper.setOnZoom(event -> {
            event.consume();
            double zoomFactor = event.getZoomFactor();

            double newScaleX = canvasWrapper.getScaleX() * zoomFactor;
            double newScaleY = canvasWrapper.getScaleY() * zoomFactor;

            if (newScaleX >= 0.5 && newScaleX <= 5.0) {
                canvasWrapper.setScaleX(newScaleX);
                canvasWrapper.setScaleY(newScaleY);
            }
        });

        // 3. RIGHT (PSEUDO CODE)
        VBox rightPane = new VBox();
        rightPane.setMinWidth(0); // Cho phép thu nhỏ hết cỡ
        rightPane.setPadding(new Insets(10));
        rightPane.setStyle("-fx-background-color: #f4f6f7;"); // Không cần border left vì SplitPane có thanh chia

        Label lblCode = new Label("Pseudo Code");
        lblCode.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-font-size: 14px;");

        VBox codeContainer = new VBox(5);
        pseudoCode = new PseudoCodeBlock(codeContainer);
        rightPane.getChildren().addAll(lblCode, codeContainer);

        // 4. SPLIT PANE (KẾT HỢP CENTER VÀ RIGHT)
        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(scrollPane, rightPane); // Trái: Canvas, Phải: Code
        splitPane.setDividerPositions(0.75); // Canvas chiếm 75%
        SplitPane.setResizableWithParent(rightPane, false); // Ưu tiên resize Canvas

        // Đặt SplitPane vào giữa Root
        root.setCenter(splitPane);

        // 5. BOTTOM (CONTROLS)
        HBox controls = createControls(canvas);
        root.setBottom(controls);

        // [INIT] Khởi tạo hệ thống
        initializeSystem(mainApp.getSelectedTreeType(), canvas);

        mainApp.switchScene(root, 1280, 800);
    }
    private HBox createControls(Canvas cv) {
        HBox box = new HBox(15);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1 0 0 0;");

        tfInput = new TextField();
        tfInput.setPromptText("Value...");
        tfInput.setPrefWidth(60);

        tfParentInput = new TextField();
        tfParentInput.setPromptText("Parent");
        tfParentInput.setPrefWidth(60);
        // Trạng thái ẩn/hiện sẽ do initializeSystem quyết định

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

        // ComboBox Type
        Label lblType = new Label("Type:");
        cboTreeType = new ComboBox<>();
        cboTreeType.getItems().addAll(TreeType.values()); // Nạp toàn bộ Enum

        // Hiển thị tên đẹp (DisplayName) thay vì tên Enum thô
        cboTreeType.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(TreeType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDisplayName());
            }
        });
        cboTreeType.setButtonCell(cboTreeType.getCellFactory().call(null)); // Hiển thị cả khi đã chọn

        cboTreeType.setValue(mainApp.getSelectedTreeType());
        cboTreeType.setOnAction(e -> {
            TreeType newType = cboTreeType.getValue();
            mainApp.setSelectedTreeType(newType);
            initializeSystem(newType, cv);
        });

        Separator sepTraversal = new Separator();
        sepTraversal.setOrientation(javafx.geometry.Orientation.VERTICAL);

        // 2. ComboBox chọn loại duyệt (BFS, DFS...)
        cboTraversal = new ComboBox<>();
        cboTraversal.getItems().addAll(TraversalType.values()); // Nạp toàn bộ Enum
        cboTraversal.setValue(TraversalType.BFS); // Mặc định chọn BFS
        cboTraversal.setPrefWidth(120);

        // 3. Nút Traverse
        btnTraverse = new Button("Traverse");
        btnTraverse.getStyleClass().add("button"); // Style giống các nút khác
        btnTraverse.setOnAction(e -> handleTraverse());

        box.getChildren().addAll(
                tfParentInput, tfInput, btnInsert, btnDelete, btnSearch,
                new Separator(),
                btnRandom,
                sepTraversal, cboTraversal, btnTraverse, // <-- Thêm 3 cái này vào
                new Separator(),
                lblSpeed, sliderSpeed, new Separator(),
                lblType, cboTreeType
        );
        return box;
    }

    // [HÀM QUAN TRỌNG] Khởi tạo hệ thống dựa trên Enum
    private void initializeSystem(TreeType type, Canvas cv) {
        // 1. Cấu hình ô nhập Parent (Dựa trên cờ trong Enum)
        if (type.isRequiresParentInput()) {
            tfParentInput.setVisible(true);
            tfParentInput.setManaged(true);
        } else {
            tfParentInput.setVisible(false);
            tfParentInput.setManaged(false);
        }

        // 2. Tạo cây mới từ Factory trong Enum (Bạn cần thêm Supplier vào TreeType như đã bàn)
        // Nếu chưa thêm, bạn có thể dùng tạm switch case ở đây, nhưng tốt nhất là dùng type.createInstance()
        this.tree = type.createTreeInstance();

        // 3. Lấy Strategy Insert tương ứng từ Enum để hiển thị mã giả
        PseudoCodeStrategy strategy = type.getInsertStrategy();
        pseudoCode.setCode(strategy.getTitle(), strategy.getLines());

        // 4. Khởi tạo Visualizer (Nó sẽ tự lấy Renderer từ Enum)
        visualizer = new TreeVisualizer(tree, type, cv);

        controller = new AnimationController(tree, visualizer, pseudoCode);
        controller.setSpeed(sliderSpeed != null ? sliderSpeed.getValue() : 2.0);
        visualizer.render();
    }

    private void handleInsert() {
        TreeType currentType = mainApp.getSelectedTreeType();
        try {
            int childValue = Integer.parseInt(tfInput.getText().trim());

            // Logic Insert Đa hình
            if (currentType.isRequiresParentInput()) {
                String parentText = tfParentInput.getText().trim();
                if (parentText.isEmpty()) {
                    controller.startInsert(childValue); // Insert Root
                } else {
                    int parentValue = Integer.parseInt(parentText);
                    controller.startInsert(parentValue, childValue);
                }
            } else {
                controller.startInsert(childValue);
            }
            // Reset input
            tfInput.clear();
            if(tfParentInput.isVisible()) tfParentInput.clear();
            tfInput.requestFocus();
        } catch (NumberFormatException e) {
            mainApp.showAlert("Input Error", "Please enter valid number!");
        }
    }

    private void handleDelete() {
        try {
            int value = Integer.parseInt(tfInput.getText().trim());
            controller.startDelete(value);
            tfInput.clear();
        } catch (NumberFormatException e) {
            mainApp.showAlert("Input Error", "Invalid number!");
        }
    }

    private void handleSearch() {
        try {
            int value = Integer.parseInt(tfInput.getText().trim());
            controller.startSearch(value);
            tfInput.clear();
        } catch (NumberFormatException e) {
            mainApp.showAlert("Input Error", "Invalid number!");
        }
    }

    private void handleRandom() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            controller.startInsert(rand.nextInt(99) + 1);
        }
    }

    private void handleTraverse() {
        TraversalType selectedType = cboTraversal.getValue();

        if (selectedType != null) {
            controller.startTraversal(selectedType);
        }
    }
}