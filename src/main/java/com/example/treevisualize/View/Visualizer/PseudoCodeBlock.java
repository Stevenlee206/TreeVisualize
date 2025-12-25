package com.example.treevisualize.View.Visualizer;

import com.example.treevisualize.Model.Node.Node;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class PseudoCodeBlock implements TreeObserver {
    private VBox container;
    private Label titleLabel;
    private VBox codeContentBox;
    private List<Label> lineLabels;
    private Label statusLabel;

    public PseudoCodeBlock(VBox container) {
        this.container = container;
        this.lineLabels = new ArrayList<>();

        this.container.setSpacing(5);
        this.container.setPadding(new Insets(10));
        this.container.setStyle("-fx-background-color: white; -fx-border-color: #333; -fx-border-width: 1px;");

        this.titleLabel = new Label("Algorithm");
        this.titleLabel.getStyleClass().add("pseudo-title");
        Separator topSep = new Separator();
        topSep.setStyle("-fx-background-color: black;");

        this.codeContentBox = new VBox(2);

        Separator botSep = new Separator();
        botSep.setStyle("-fx-background-color: black;");

        this.statusLabel = new Label();
        this.statusLabel.getStyleClass().add("pseudo-status-error");
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
            label.getStyleClass().add("pseudo-line-normal");
            label.setMaxWidth(Double.MAX_VALUE);
            label.setWrapText(false);// không xuống dòng tự động

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
            lbl.getStyleClass().removeAll("pseudo-line-normal", "pseudo-line-highlight");
            if (i == lineIndex) {
                lbl.getStyleClass().add("pseudo-line-highlight");
            } else {
                lbl.getStyleClass().add("pseudo-line-normal");
            }
        }
    }

    public void clearHighlight() {
        for (Label lbl : lineLabels) {
            lbl.getStyleClass().removeAll("pseudo-line-normal", "pseudo-line-highlight");
            lbl.getStyleClass().add("pseudo-line-normal");        }
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
    @Override
    public void onPseudoStep(int lineIndex) {
        highlightLine(lineIndex);
    }
}