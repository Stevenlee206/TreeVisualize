package Visualizer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
public class NodeVisualizer {
    private int value;
    private double x,y;
    private Text label;
    private double radius;
    private Color fillColor;

    public void draw(GraphicsContext gc){
        gc.setFill(this.fillColor);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        // Border
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

        // Bây giờ bạn chỉ cần truyền đúng toạ độ TÂM (x, y)
        // JavaFX sẽ tự động vẽ chữ nằm chính giữa tâm đó.
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
