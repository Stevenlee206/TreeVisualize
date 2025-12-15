package com.example.treevisualize.PseudoCodeStore;

// Import tất cả các gói chiến lược
import com.example.treevisualize.PseudoCodeStore.Insert.*;
import com.example.treevisualize.PseudoCodeStore.Traversal.*;
import com.example.treevisualize.PseudoCodeStore.Delete.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PseudoCodeFactory {

    // 1. KHO CHỨA (REGISTRY) - Sử dụng Map để tra cứu nhanh (O(1))
    // Cần có đủ 3 Map cho 3 hành động chính
    private static final Map<String, PseudoCodeStrategy> insertRegistry = new HashMap<>();
    private static final Map<String, PseudoCodeStrategy> deleteRegistry = new HashMap<>();
    private static final Map<String, PseudoCodeStrategy> traversalRegistry = new HashMap<>();

    // Strategy rỗng (Null Object Pattern) để tránh lỗi null pointer khi không tìm thấy key
    private static final PseudoCodeStrategy EMPTY_STRATEGY = new PseudoCodeStrategy() {
        @Override public String getTitle() { return ""; }
        @Override public List<String> getLines() { return Collections.emptyList(); }
    };

    // 2. KHỐI STATIC: Tự động đăng ký các thuật toán khi chương trình khởi chạy
    static {
        // --- ĐĂNG KÝ INSERT ---
        // Key phải khớp chính xác với chuỗi trong ComboBox của Main3
        insertRegistry.put("Red Black Tree",       new RedBlackTreeInsert());
        insertRegistry.put("Binary Search Tree",   new BSTInsert());
        insertRegistry.put("Binary Tree (Normal)", new BTInsert());

        // --- ĐĂNG KÝ DELETE ---
        deleteRegistry.put("Red Black Tree",       new RBTDeleteStrategy());
        deleteRegistry.put("Binary Search Tree",   new BSTDeleteStrategy());
        // Binary Tree thường dùng chung logic xóa hoặc chưa hỗ trợ, ta gán tạm BST hoặc Rỗng
        deleteRegistry.put("Binary Tree (Normal)", new BSTDeleteStrategy());

        // --- ĐĂNG KÝ TRAVERSAL ---
        traversalRegistry.put("In-Order (LNR)",      new InOrderPCode());
        traversalRegistry.put("Pre-Order (NLR)",     new PreOrderPCode());
        traversalRegistry.put("Post-Order (LRN)",    new PostOrderPCode());
        traversalRegistry.put("BFS (Level Order)",   new BFSPCode());
    }

    // 3. CÁC HÀM TRUY XUẤT (GETTERS)

    /**
     * Lấy chiến lược Insert từ kho.
     */
    public static PseudoCodeStrategy getInsertStrategy(String treeType) {
        return insertRegistry.getOrDefault(treeType, EMPTY_STRATEGY);
    }

    /**
     * Lấy chiến lược Delete từ kho.
     */
    public static PseudoCodeStrategy getDeleteStrategy(String treeType) {
        return deleteRegistry.getOrDefault(treeType, EMPTY_STRATEGY);
    }

    /**
     * Lấy chiến lược Traversal từ kho.
     */
    public static PseudoCodeStrategy getTraversalStrategy(String traversalType) {
        return traversalRegistry.getOrDefault(traversalType, EMPTY_STRATEGY);
    }

    // 4. [MỞ RỘNG] ĐĂNG KÝ ĐỘNG (DYNAMIC REGISTRATION)
    // Cho phép thêm thuật toán mới lúc runtime mà không cần sửa code class này

    public static void registerInsert(String name, PseudoCodeStrategy strategy) {
        insertRegistry.put(name, strategy);
    }

    public static void registerDelete(String name, PseudoCodeStrategy strategy) {
        deleteRegistry.put(name, strategy);
    }

    public static void registerTraversal(String name, PseudoCodeStrategy strategy) {
        traversalRegistry.put(name, strategy);
    }
}