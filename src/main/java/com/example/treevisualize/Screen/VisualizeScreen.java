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
import javafx.scene.paint.Color;

import java.util.Random;

public class VisualizeScreen {

    private final Main5 mainApp;
    private Tree tree;
    private TreeVisualizer visualizer;
    private AnimationController controller;
    private PseudoCodeBlock pseudoCode;
    private TextField tfParentInput;
    private TextField tfInput;
    private ComboBox<String> cboTreeType;
    private Slider sliderSpeed;

    public VisualizeScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("visualizer-pane");

        Button btnHome = new Button("🏠 Home");
        btnHome.getStyleClass().add("button");
        btnHome.setOnAction(e -> mainApp.switchToIntroScreen());

        Label lblTitle = new Label("Visualizer Mode");
        lblTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ToolBar topBar = new ToolBar(btnHome, new Separator(), lblTitle);
        root.setTop(topBar);

        Pane canvasPane = new Pane();
        canvasPane.setStyle("-fx-background-color: #ffffff;");

        Canvas canvas = new Canvas(1000, 600);
        canvas.widthProperty().bind(canvasPane.widthProperty());
        canvas.heightProperty().bind(canvasPane.heightProperty());

        canvasPane.getChildren().add(canvas);
        root.setCenter(canvasPane);

        VBox rightPane = new VBox();
        rightPane.setPrefWidth(350);
        rightPane.setPadding(new Insets(10));
        rightPane.setStyle("-fx-background-color: #f4f6f7; -fx-border-color: #bdc3c7; -fx-border-width: 0 0 0 1;");

        Label lblCode = new Label("Pseudocode");
        lblCode.setStyle("-fx-font-weight: bold; -fx-underline: true; -fx-font-size: 14px;");

        VBox codeContainer = new VBox(5);
        pseudoCode = new PseudoCodeBlock(codeContainer);

        rightPane.getChildren().addAll(lblCode, codeContainer);
        root.setRight(rightPane);

        HBox controls = createControls(canvas);
        root.setBottom(controls);

        initializeSystem(mainApp.getSelectedTreeType(), canvas);

        mainApp.switchScene(root, 1280, 800);
    }

    private HBox createControls(Canvas cv) {
        HBox box = new HBox(15);
        box.setPadding(new Insets(15));
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1 0 0 0;");

        tfInput = new TextField();
        tfInput.setPromptText("Enter number...");
        tfInput.setPrefWidth(80);

        tfParentInput = new TextField();
        tfParentInput.setPromptText("Parent");
        tfParentInput.setPrefWidth(70);
        tfParentInput.setVisible(false);
        tfParentInput.setManaged(false);

        Button btnInsert = new Button("Insert");
        btnInsert.getStyleClass().add("btn-primary");
        btnInsert.setOnAction(e -> handleInsert());

        Button btnDelete = new Button("Delete");
        btnDelete.getStyleClass().add("button");
        btnDelete.setStyle("-fx-text-fill: red;");
        btnDelete.setOnAction(e -> handleDelete());

        Button btnSearch = new Button("Search");
        btnSearch.getStyleClass().add("button");
        btnSearch.setOnAction(e -> handleSearch());

        Button btnRandom = new Button("Random (10)");
        btnRandom.getStyleClass().add("button");
        btnRandom.setOnAction(e -> handleRandom());

        Label lblSpeed = new Label("Speed:");
        sliderSpeed = new Slider(1, 5, 2);
        sliderSpeed.setShowTickMarks(true);
        sliderSpeed.setShowTickLabels(true);
        sliderSpeed.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (controller != null) controller.setSpeed(newVal.doubleValue());
        });

        Label lblType = new Label("Type:");
        cboTreeType = new ComboBox<>();
        cboTreeType.getItems().addAll(
                "Red Black Tree",
                "Binary Search Tree",
                "Binary Tree (Normal)",
                "General Tree"
        );
        cboTreeType.setValue(mainApp.getSelectedTreeType());
        cboTreeType.setOnAction(e -> {
            String newType = cboTreeType.getValue();
            mainApp.setSelectedTreeType(newType);
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

    private void initializeSystem(String treeType, Canvas cv) {
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
            case "Red Black Tree":
            default:
                tree = new RedBlackTree();
                break;
        }

        PseudoCodeStrategy strategy = PseudoCodeFactory.getInsertStrategy(treeType);
        pseudoCode.setCode(strategy.getTitle(), strategy.getLines());

        visualizer = new TreeVisualizer(tree, cv);

        controller = new AnimationController(tree, visualizer, pseudoCode);

        controller.setSpeed(sliderSpeed != null ? sliderSpeed.getValue() : 2.0);

        visualizer.render();
    }

    private void handleInsert() {
        String currentType = mainApp.getSelectedTreeType();

        try {
            int childValue = Integer.parseInt(tfInput.getText().trim());

            if ("General Tree".equals(currentType)) {
                String parentText = tfParentInput.getText().trim();

                if (parentText.isEmpty()) {
                    controller.startInsert(childValue);
                } else {
                    int parentValue = Integer.parseInt(parentText);
                    controller.startInsert(parentValue, childValue);
                }

            } else {
                controller.startInsert(childValue);
            }

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
            mainApp.showAlert("Input Error", "Please enter valid number to delete!");
        }
    }

    private void handleSearch() {
        try {
            int value = Integer.parseInt(tfInput.getText().trim());
            controller.startSearch(value);
            tfInput.clear();
        } catch (NumberFormatException e) {
            mainApp.showAlert("Input Error", "Please enter valid number to search!");
        }
    }

    private void handleRandom() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int val = rand.nextInt(99) + 1;
            controller.startInsert(val);
        }
    }
}
