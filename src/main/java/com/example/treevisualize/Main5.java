package com.example.treevisualize;

import com.example.treevisualize.Screen.InfoScreen;
import com.example.treevisualize.Screen.IntroScreen;
import com.example.treevisualize.Screen.SelectScreen;
import com.example.treevisualize.Screen.VisualizeScreen;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Main5 extends Application {

    private Stage primaryStage;

    // --- GLOBAL STATE (DỮ LIỆU DÙNG CHUNG CHO TOÀN APP) ---
    // Mặc định là Red Black Tree, biến này sẽ thay đổi khi chọn ở SelectScreen
    private String selectedTreeType = "Red Black Tree";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setTitle("Tree Visualization App");
        primaryStage.setMaximized(true);

        // Set Icon cho ứng dụng (Nếu có)
        try {
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/TreeLogo.png"))));
        } catch (Exception ignored) { }

        // BẮT ĐẦU VÀO MÀN HÌNH INTRO
        switchToIntroScreen();
        primaryStage.show();
    }

    // =================================================================
    // CÁC HÀM ĐIỀU HƯỚNG (NAVIGATION)
    // Các Screen class sẽ gọi các hàm này để chuyển cảnh
    // =================================================================

    public void switchToIntroScreen() {
        // Tạo object màn hình Intro và hiển thị
        new IntroScreen(this).show();
    }

    public void switchToSelectScreen() {
        // Tạo object màn hình Select và hiển thị
        new SelectScreen(this).show();
    }

    public void switchToInfoScreen() {
        // Tạo object màn hình Info và hiển thị
        new InfoScreen(this).show();
    }

    public void switchToVisualizerScreen() {
        // Tạo object màn hình Visualizer (Logic phức tạp nằm hết trong class này)
        new VisualizeScreen(this).show();
    }

    // =================================================================
    // HÀM TIỆN ÍCH CHUNG (UTILITIES)
    // =================================================================

    /**
     * Hàm thay đổi Scene gốc của Stage với hiệu ứng Fade
     */
    public void switchScene(Parent root, double width, double height) {
        Scene scene = new Scene(root, width, height);

        // Nạp CSS Global
        try {
            String css = Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm();
            scene.getStylesheets().add(css);
        } catch (Exception e) {
            System.err.println("Error: Không tìm thấy file style.css!");
        }

        // Hiệu ứng chuyển cảnh (Fade In)
        root.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(400), root);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        primaryStage.setScene(scene);
        // Căn giữa màn hình máy tính mỗi khi đổi kích thước cửa sổ
        primaryStage.centerOnScreen();
    }

    /**
     * Hàm hiển thị thông báo lỗi/cảnh báo chung
     */
    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // =================================================================
    // GETTER & SETTER CHO GLOBAL STATE
    // =================================================================

    public String getSelectedTreeType() {
        return selectedTreeType;
    }

    public void setSelectedTreeType(String selectedTreeType) {
        this.selectedTreeType = selectedTreeType;
        System.out.println("Main3 Log: User selected tree -> " + selectedTreeType);
    }
}