package com.example.treevisualize.Description;

import java.util.HashMap;
import java.util.Map;

public class DescriptionFactory {

    private static final Map<String, Description> registry = new HashMap<>();

    // Default Strategy
    private static final Description EMPTY = () -> "Chưa có mô tả.";

    static {
        registry.put("Red Black Tree",       new RBTDescription());
        registry.put("Binary Search Tree",   new BSTDescription());
        registry.put("Binary Tree (Normal)", new BinaryTreeDescription());
        registry.put("General Tree",         new GeneralTreeDescription());
    }
    public static Description getStrategy(String treeType) {
        return registry.getOrDefault(treeType, EMPTY);
    }

    // --- 2. THÊM HÀM NÀY: Cho phép các module bên ngoài tự đăng ký ---
    public static void register(String treeType, Description strategy) {
        registry.put(treeType, strategy);
    }
}