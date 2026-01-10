package com.example.treevisualize.View.Visualizer;

import javafx.scene.paint.Color;

public class ColorUtils {
    public static boolean isDark(Color color) {
        if (color == null) return false;
        double brightness = (color.getRed() * 0.299) +
                (color.getGreen() * 0.587) +
                (color.getBlue() * 0.114);
        return brightness < 0.5;
    }
}
