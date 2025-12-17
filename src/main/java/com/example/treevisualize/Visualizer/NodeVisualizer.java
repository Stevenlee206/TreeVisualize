package com.example.treevisualize.Visualizer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
public class NodeVisualizer {
    private int value;
    private double x,y;
    private Text label;
    private double radius;
    private Color fillColor;

    public NodeVisualizer() {
        this.label = new Text();
        this.label.setFont(new Font("Arial", 14));
    }

    public void draw(GraphicsContext gc){
        gc.setFill(this.fillColor);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Border
        /*setStroke() trong JavaFX dùng để thiết lập màu sắc cho đường viền (outline) của các đối tượng đồ họa */
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        if (isDark(this.fillColor)) {
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

    private boolean isDark(Color color) {
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        double brightness = (r * 0.299) + (g * 0.587) + (b * 0.114);
        return brightness < 0.5;
    }
}
