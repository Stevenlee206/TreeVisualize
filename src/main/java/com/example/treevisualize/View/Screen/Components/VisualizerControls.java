package com.example.treevisualize.View.Screen.Components;

import com.example.treevisualize.Model.Description.TreeType;
import com.example.treevisualize.Shared.TraversalType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class VisualizerControls extends HBox {

    // Interface để báo cáo sự kiện lên cấp trên
    public interface ControlListener {
        void onInsert(int value);
        void onInsertGeneral(int parent, int child);
        void onDelete(int value);
        void onSearch(int value);
        void onRandom(int count);
        void onTraverse(TraversalType type);
        void onTreeTypeChanged(TreeType newType);
        void onSpeedChanged(double newSpeed);
        void onUndo();
        void onRedo();
        void onReplay();
    }

    private final TextField tfParentInput;
    private final TextField tfInput;
    private final ComboBox<TreeType> cboTreeType;
    private final Slider sliderSpeed;
    private final ComboBox<TraversalType> cboTraversal;
    private ControlListener listener;

    public VisualizerControls(TreeType initialType) {
        this.setPadding(new Insets(15));
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #ecf0f1; -fx-border-color: #bdc3c7; -fx-border-width: 1 0 0 0;");

        // --- Init Components ---
        tfParentInput = new TextField();
        tfParentInput.setPromptText("Parent");
        tfParentInput.setPrefWidth(60);

        tfInput = new TextField();
        tfInput.setPromptText("Value...");
        tfInput.setPrefWidth(60);

        Button btnInsert = createButton("Insert", "btn-primary", this::handleInsert);
        Button btnDelete = createButton("Delete", "button", this::handleDelete);
        btnDelete.setStyle("-fx-text-fill: red;");
        Button btnSearch = createButton("Search", "button", this::handleSearch);
        Button btnRandom = createButton("Random", "button", this::handleRandom);

        Button btnUndo = createButton("<<", "button", () -> {
            if (listener != null) listener.onUndo();
        });
        Button btnReplay = createButton("Replay", "button", () -> {
            if (listener != null) listener.onReplay();
        });
        Button btnRedo = createButton(">>", "button", () -> {
            if (listener != null) listener.onRedo();
        });
        // Thêm Tooltip để người dùng hiểu nút làm gì
        btnUndo.setTooltip(new Tooltip("Undo / Step Backward"));
        btnReplay.setTooltip(new Tooltip("Replay Animation"));
        btnRedo.setTooltip(new Tooltip("Redo / Step Forward"));

        // Slider
        sliderSpeed = new Slider(1, 5, 2);
        sliderSpeed.setShowTickMarks(true);
        sliderSpeed.valueProperty().addListener((obs, old, val) -> {
            if (listener != null) listener.onSpeedChanged(val.doubleValue());
        });

        // Tree Type Combo
        cboTreeType = new ComboBox<>();
        cboTreeType.getItems().addAll(TreeType.values());
        cboTreeType.setValue(initialType);
        cboTreeType.setConverter(new StringConverter<>() {
            @Override public String toString(TreeType object) { return object.getDisplayName(); }
            @Override public TreeType fromString(String string) { return null; }
        });
        cboTreeType.setOnAction(e -> {
            if (listener != null) listener.onTreeTypeChanged(cboTreeType.getValue());
        });

        // Traversal
        cboTraversal = new ComboBox<>();
        cboTraversal.getItems().addAll(TraversalType.values());
        cboTraversal.setValue(TraversalType.BFS);
        Button btnTraverse = createButton("Traverse", "button", () -> {
            if (listener != null) listener.onTraverse(cboTraversal.getValue());
        });

        // Layout
        this.getChildren().addAll(
                tfParentInput, tfInput, btnInsert, btnDelete, btnSearch,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                btnRandom,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                btnUndo, btnReplay, btnRedo,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                cboTraversal, btnTraverse,
                new Separator(javafx.geometry.Orientation.VERTICAL),
                new Label("Speed:"), sliderSpeed,
                new Label("Type:"), cboTreeType
        );

        updateUIForType(initialType);
    }

    public void setListener(ControlListener listener) {
        this.listener = listener;
    }

    // Hàm cập nhật giao diện (Ẩn hiện ô Parent)
    public void updateUIForType(TreeType type) {
        boolean showParent = type.isRequiresParentInput();
        tfParentInput.setVisible(showParent);
        tfParentInput.setManaged(showParent);
        // Sync combo box nếu bị đổi từ bên ngoài
        if (cboTreeType.getValue() != type) cboTreeType.setValue(type);
    }

    public double getCurrentSpeed() { return sliderSpeed.getValue(); }

    // --- Private Handlers (Validation Logic) ---

    private void handleInsert() {
        if (listener == null) return;
        try {
            String valTxt = tfInput.getText().trim();
            if (valTxt.isEmpty()) return;
            int val = Integer.parseInt(valTxt);

            if (tfParentInput.isVisible()) {
                String pTxt = tfParentInput.getText().trim();
                if (pTxt.isEmpty()) listener.onInsertGeneral(-1, val); // Insert Root
                else listener.onInsertGeneral(Integer.parseInt(pTxt), val);
            } else {
                listener.onInsert(val);
            }
            tfInput.clear();
            if (tfParentInput.isVisible()) tfParentInput.clear();
            tfInput.requestFocus();
        } catch (NumberFormatException e) {
            showAlert("Invalid Number");
        }
    }

    private void handleDelete() {
        handleIntAction(val -> listener.onDelete(val));
    }

    private void handleSearch() {
        handleIntAction(val -> listener.onSearch(val));
    }

    private void handleRandom() {
        // Sửa số 10 thành số 1
        if (listener != null) listener.onRandom(1);
    }

    // Helper rút gọn code
    private void handleIntAction(java.util.function.IntConsumer action) {
        if (listener == null) return;
        try {
            String txt = tfInput.getText().trim();
            if (!txt.isEmpty()) {
                action.accept(Integer.parseInt(txt));
                tfInput.clear();
                tfInput.requestFocus();
            }
        } catch (NumberFormatException e) { showAlert("Invalid Number"); }
    }

    private Button createButton(String text, String styleClass, Runnable action) {
        Button b = new Button(text);
        b.getStyleClass().add(styleClass);
        b.setOnAction(e -> action.run());
        return b;
    }

    private void showAlert(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }
}