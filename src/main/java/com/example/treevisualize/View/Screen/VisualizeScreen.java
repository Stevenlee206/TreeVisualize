package com.example.treevisualize.View.Screen;

import com.example.treevisualize.Controller.AnimationController;
import com.example.treevisualize.Model.Description.TreeType;
import com.example.treevisualize.demo.Main5;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.View.Screen.Components.VisualizerCanvas;
import com.example.treevisualize.View.Screen.Components.VisualizerControls;
import com.example.treevisualize.Shared.TraversalType;
import com.example.treevisualize.Model.Tree.Tree;
import com.example.treevisualize.View.Visualizer.PseudoCodeBlock;
import com.example.treevisualize.View.Visualizer.TreeVisualizer;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
public class VisualizeScreen implements VisualizerControls.ControlListener {

    private final Main5 mainApp;
    
    private TextField txtNodes;
    private TextField txtDepth;

    // Components (Modules)
    private VisualizerCanvas canvasView;
    private VisualizerControls controlsView;
    private PseudoCodeBlock pseudoCodeView;

    // Logic
    private Tree tree;
    private TreeVisualizer visualizer;
    private AnimationController controller;

    public VisualizeScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        BorderPane root = new BorderPane();

        // 1. Top Bar
        Button btnHome = new Button("Home");
        btnHome.getStyleClass().add("button");
        btnHome.setOnAction(e -> mainApp.switchToIntroScreen());

        ComboBox<TreeType> cboTreeType = new ComboBox<>();
        cboTreeType.getStyleClass().add("combo-box");
        cboTreeType.getItems().addAll(TreeType.values());
        cboTreeType.setValue(mainApp.getSelectedTreeType());
        cboTreeType.setConverter(new StringConverter<>() {
            @Override
            public String toString(TreeType type) {
                return type.getDisplayName();
            }
            @Override
            public TreeType fromString(String string) {
                return null;
            }
        });

        cboTreeType.setOnAction(e -> {
            TreeType newType = cboTreeType.getValue();
            mainApp.setSelectedTreeType(newType);
            initializeSystem(newType);
        });
        
        txtNodes = new TextField("0");
        txtNodes.setPrefWidth(50);
        txtNodes.setEditable(false);
        txtNodes.setFocusTraversable(false);
        
        txtDepth = new TextField("0");
        txtDepth.setPrefWidth(50);
        txtDepth.setEditable(false);
        txtDepth.setFocusTraversable(false);

        ToolBar topBar = new ToolBar(
                btnHome,
                new Separator(),
                new Label("Tree Type:"),
                cboTreeType,
                new Separator(),
                new Label("Number of nodes:"),
                txtNodes,
                new Separator(),
                new Label("Depth:"),
                txtDepth
        );

        root.setTop(topBar);

        // 2. Center: Canvas Module
        canvasView = new VisualizerCanvas(1200, 800);

        // 3. Right: PseudoCode Module
        VBox rightPane = new VBox(5);
        rightPane.setPadding(new Insets(10));
        pseudoCodeView = new PseudoCodeBlock(rightPane);

        // SplitPane
        SplitPane splitPane = new SplitPane(canvasView, rightPane);
        splitPane.setDividerPositions(0.75);
        root.setCenter(splitPane);

        // 4. Bottom: Controls Module
        controlsView = new VisualizerControls(mainApp.getSelectedTreeType());
        controlsView.setListener(this); // Đăng ký lắng nghe sự kiện
        root.setBottom(controlsView);

        // 5. Init Logic
        initializeSystem(mainApp.getSelectedTreeType());

        mainApp.switchScene(root);
    }

    // --- LOGIC KHỞI TẠO ---
    private void initializeSystem(TreeType type) {
        // 1. Update UI components
        controlsView.updateUIForType(type);

        // 2. Logic Setup
        this.tree = type.createTreeInstance();

        PseudoCodeStrategy strategy = type.getInsertStrategy();
        if (strategy != null) pseudoCodeView.setCode(strategy.getTitle(), strategy.getLines());

        this.visualizer = new TreeVisualizer(tree, type, canvasView.getCanvas());

        // 3. Bind Visualizer to CanvasView
        canvasView.bindVisualizer(this.visualizer);

        // 4. Controller Setup
        this.controller = new AnimationController(tree, visualizer, pseudoCodeView, type);
        this.controller.setSpeed(controlsView.getCurrentSpeed());
        controller.setOnFinished(this::updateTreeStats);
        visualizer.render();
        updateTreeStats();
    }
    
    public void updateTreeStats() {
    	if (tree == null) return;
    	txtNodes.setText(String.valueOf(tree.getNodeCount()));
    	txtDepth.setText(String.valueOf(tree.getHeight()));
    }

    // --- TRIỂN KHAI LISTENER (Xử lý sự kiện từ Controls) ---

    @Override
    public void onInsert(int value) {
        controller.startInsert(value);
    }

    @Override
    public void onInsert(int parent, int child) {
        controller.startInsert(parent, child);
    }

    @Override
    public void onDelete(int value) {
        controller.startDelete(value);
    }

    @Override
    public void onSearch(int value) {
        controller.startSearch(value);
    }

    @Override
    public void onTraverse(TraversalType type) {
        controller.startTraversal(type);
    }

    @Override
    public void onRandom(int count) {
        controller.startRandomBatch(count);
    }
    @Override
    public void onUndo() {
        if (controller != null) {
        	controller.stepBackward();
        }
    }

    @Override
    public void onRedo() {
        if (controller != null) {
        	controller.stepForward();
        }
    }

    @Override
    public void onReplay() {
        if (controller != null) {
        	controller.replay();
        }
    }


    @Override
    public void onSpeedChanged(double newSpeed) {
        if (controller != null) controller.setSpeed(newSpeed);
    }

    @Override
    public void onClear() {
        // 1. Create a confirmation dialog in English
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Clear");
        alert.setHeaderText("Are you sure you want to clear the tree?");
        alert.setContentText("This action will delete all nodes and reset the animation history.");

        // 2. Handle the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // 3. Clear data in Model
                if (tree != null) {
                    tree.clear();
                }

                // 4. Reset history in Controller
                if (controller != null) {
                    controller.reset();
                }

                // 5. Update stats (Nodes and Depth will become 0)
                updateTreeStats(); //

                // 6. Force immediate re-render of the empty canvas
                if (visualizer != null) {
                    visualizer.render();
                }
            }
        });
    }
}