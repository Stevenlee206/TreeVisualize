package com.example.treevisualize.Visualizer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
public class NodeVisualizer {
    private int value;
    private double x,y;
    private Text label;
    private double radius;
    private Color fillColor;
    private static final Font NODE_FONT = Font.font("Arial", FontWeight.BOLD, 14);

    public NodeVisualizer() {
        this.label = new Text();
        this.label.setFont(NODE_FONT);
    }

    public void draw(GraphicsContext gc){
        gc.setFill(this.fillColor);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Border
        /*setStroke() trong JavaFX dùng để thiết lập màu sắc cho đường viền (outline) của các đối tượng đồ họa */
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        if (ColorUtils.isDark(this.fillColor)) {
            gc.setFill(Color.WHITE);
        } else {
            gc.setFill(Color.BLACK);
        }
        gc.setFont(this.label.getFont());

        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);

        gc.setTextBaseline(javafx.geometry.VPos.CENTER);

        gc.fillText(String.valueOf(value), x, y);
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setValue(int value) {
        this.value = value;
        this.label.setText(String.valueOf(value));
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void updatePosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

}
