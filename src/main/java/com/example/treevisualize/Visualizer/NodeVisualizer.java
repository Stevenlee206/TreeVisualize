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
        /*
        Font(String name, double size): Tạo font với tên họ và kích thước (ví dụ: new Font("Arial", 14)).
         */
        this.label.setFont(new Font("Arial", 14));
    }
    /*
    fillOval() là một phương thức của lớp GraphicsContext (thường dùng trên Canvas) dùng để vẽ
    và tô màu đầy đặn một hình bầu dục (hoặc hình tròn nếu chiều rộng bằng chiều cao), được xác định
     */
    public void draw(GraphicsContext gc){
        /*
        setFill() là phương thức dùng để thiết lập màu nền (màu tô) cho các đối tượng đồ họa như Shape (Hình dạng)
        , Text, Label, và Button, chấp nhận một đối tượng thuộc lớp cơ sở Paint (màu đơn sắc hoặc gradient).
         */
        gc.setFill(this.fillColor);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Border
        /*setStroke() trong JavaFX dùng để thiết lập màu sắc cho đường viền (outline) của các đối tượng đồ họa */
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.5);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

        // 3. Vẽ số (Value)
        // B3.1: Chọn màu chữ tương phản (Nền tối -> Chữ trắng, Nền sáng -> Chữ đen)
        if (isDark(this.fillColor)) {
            gc.setFill(Color.WHITE); // Nền tối -> Chữ trắng mới đọc được
        } else {
            gc.setFill(Color.BLACK); // Nền sáng -> Chữ đen mới đọc được
        }
        gc.setFont(this.label.getFont());

        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);

        // Thiết lập đường cơ sở (Baseline) là GIỮA theo chiều DỌC
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
