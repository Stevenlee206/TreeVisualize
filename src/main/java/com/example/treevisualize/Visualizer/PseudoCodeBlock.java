package com.example.treevisualize.Visualizer;

import com.example.treevisualize.Node.Node;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class PseudoCodeBlock implements TreeObserver {
    private VBox container;
    private Label titleLabel;
    private VBox codeContentBox;
    private List<Label> lineLabels;
    private Label statusLabel;

    private final String STYLE_HEADER =
            "-fx-font-family: 'Times New Roman'; " +
                    "-fx-font-size: 16px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-text-fill: #000000;";

    private final String STYLE_NORMAL =
            "-fx-background-color: transparent; " +
                    "-fx-text-fill: #000000; " +
                    "-fx-font-family: 'Consolas', 'Menlo', 'Courier New'; " +
                    "-fx-font-size: 14px; " +
                    "-fx-padding: 1 5 1 5;";

    private final String STYLE_HIGHLIGHT =
            "-fx-background-color: #e0e0e0; " + 
                    "-fx-text-fill: #000000; " +
                    "-fx-font-family: 'Consolas', 'Menlo', 'Courier New'; " +
                    "-fx-font-size: 14px; " +
                    "-fx-font-weight: bold; " +
                    "-fx-padding: 1 5 1 5;";

    private final String STYLE_ERROR =
            "-fx-background-color: #ffebee; " +
                    "-fx-text-fill: #c62828; " +
                    "-fx-font-family: 'Consolas'; " +
                    "-fx-font-size: 13px; " +
                    "-fx-padding: 5;";

    public PseudoCodeBlock(VBox container) {
        this.container = container;
        this.lineLabels = new ArrayList<>();

        this.container.setSpacing(5);
        this.container.setPadding(new Insets(10));
        this.container.setStyle("-fx-background-color: white; -fx-border-color: #333; -fx-border-width: 1px;");

        this.titleLabel = new Label("Algorithm");
        this.titleLabel.setStyle(STYLE_HEADER);

        Separator topSep = new Separator();
        topSep.setStyle("-fx-background-color: black;");

        this.codeContentBox = new VBox(2);

        Separator botSep = new Separator();
        botSep.setStyle("-fx-background-color: black;");

        this.statusLabel = new Label();
        this.statusLabel.setStyle(STYLE_ERROR);
        this.statusLabel.setMaxWidth(Double.MAX_VALUE);
        this.statusLabel.setVisible(false);

        this.container.getChildren().addAll(titleLabel, topSep, codeContentBox, botSep, statusLabel);
    }

    public void setCode(String title, List<String> lines) {
        this.titleLabel.setText("Algorithm: " + title);
        this.codeContentBox.getChildren().clear();
        this.lineLabels.clear();
        this.statusLabel.setVisible(false);

        for (String lineContent : lines) {
            Label label = new Label(lineContent);
            label.setStyle(STYLE_NORMAL);
            label.setMaxWidth(Double.MAX_VALUE);
            label.setWrapText(false);

            this.lineLabels.add(label);
            this.codeContentBox.getChildren().add(label);
        }
    }

    public void setCode(List<String> lines) {
        setCode("Procedure", lines);
    }

    public void highlightLine(int lineIndex) {
        if (lineIndex < 0 || lineIndex >= lineLabels.size()) return;
        this.statusLabel.setVisible(false);

        for (int i = 0; i < lineLabels.size(); i++) {
            Label lbl = lineLabels.get(i);
            if (i == lineIndex) {
                lbl.setStyle(STYLE_HIGHLIGHT);
            } else {
                lbl.setStyle(STYLE_NORMAL);
            }
        }
    }

    public void clearHighlight() {
        for (Label lbl : lineLabels) {
            lbl.setStyle(STYLE_NORMAL);
        }
        this.statusLabel.setVisible(false);
    }

    @Override
    public void onNodeChanged(Node node) { }

    @Override
    public void onStructureChanged() {
        clearHighlight();
        this.statusLabel.setVisible(false);
    }

    @Override
    public void onError(String message) {
        this.statusLabel.setText("⚠ " + message);
        this.statusLabel.setVisible(true);
    }
}