package com.example.treevisualize.View.Screen;

import com.example.treevisualize.Model.Description.TreeType;
import com.example.treevisualize.demo.Main5;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import java.util.Objects;

public class SelectScreen {
    private final Main5 mainApp;

    public SelectScreen(Main5 mainApp) {
        this.mainApp = mainApp;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("selection-pane");
        root.setPadding(new Insets(20));

        // --- 1. Header Section (Stays Fixed at Top) ---
        Button btnBack = new Button("⬅ Back");
        btnBack.getStyleClass().add("button");
        btnBack.setOnAction(e -> mainApp.switchToIntroScreen());

        VBox titleBox = new VBox(5);
        titleBox.setAlignment(Pos.CENTER);
        
        Label lblHeader = new Label("Choose Your Tree");
        lblHeader.getStyleClass().add("info-heading");
        
        Label lblSub = new Label("Select a structure to visualize immediately");
        lblSub.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");
        titleBox.getChildren().addAll(lblHeader, lblSub);

        BorderPane topHeader = new BorderPane();
        topHeader.setLeft(btnBack);
        topHeader.setCenter(titleBox);
        BorderPane.setAlignment(titleBox, Pos.CENTER);
        topHeader.setPadding(new Insets(0, 0, 10, 0)); // Reduced bottom padding slightly

        root.setTop(topHeader);

        // --- 2. Scrollable Content Section ---
        
        // Create the grid of cards
        GridPane grid = createGrid();
        
        // Wrap Grid in a VBox to ensure alignment and padding inside the scroll area
        VBox contentContainer = new VBox(grid);
        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.setPadding(new Insets(10, 20, 20, 20)); // Padding: Top, Right, Bottom, Left

        // 
        // Wrap the Container in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(contentContainer);
        
        // Critical: Make the scroll pane width match the window width so content centers
        scrollPane.setFitToWidth(true); 
        
        // UI Tweaks: Remove the default gray border/background of ScrollPane
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setPannable(true); // Allows dragging with mouse
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); 
        
        root.setCenter(scrollPane);
        
        mainApp.switchScene(root);
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);

        int col = 0;
        int row = 0;

        for (TreeType type : TreeType.values()) {
            VBox card = createSelectableCard(type);
            grid.add(card, col, row);

            col++;
            if (col > 2) { // 3 Columns Max
                col = 0;
                row++;
            }
        }
        return grid;
    }

    private VBox createSelectableCard(TreeType type) {
        VBox card = new VBox(15);
        card.getStyleClass().add("tree-card");
        card.setAlignment(Pos.CENTER);
        
        // Ensure card takes up space in grid but doesn't explode
        GridPane.setHgrow(card, Priority.ALWAYS);
        GridPane.setVgrow(card, Priority.ALWAYS);

        ImageView iconView = new ImageView();
        try {
            String path = (type.getIconPath() != null) ? type.getIconPath() : "/images/TreeView.png";
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
            iconView.setImage(img);
            iconView.setFitHeight(64);
            iconView.setPreserveRatio(true);
        } catch (Exception e) { /* Ignored */ }

        Label lblTitle = new Label(type.getDisplayName());
        lblTitle.getStyleClass().add("card-title");
        lblTitle.setTextAlignment(TextAlignment.CENTER);
        lblTitle.setWrapText(true);

        Label lblDesc = new Label("Click to explore");
        lblDesc.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 12px;");

        card.getChildren().addAll(iconView, lblTitle, lblDesc);

        card.setOnMouseClicked(e -> {
            mainApp.setSelectedTreeType(type);
            mainApp.switchToInfoScreen();
        });

        return card;
    }
}