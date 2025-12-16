package com.example.treevisualize.PseudoCodeStore;


import com.example.treevisualize.PseudoCodeStore.Insert.*;
import com.example.treevisualize.PseudoCodeStore.Traversal.*;
import com.example.treevisualize.PseudoCodeStore.Delete.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PseudoCodeFactory {

    private static final Map<String, PseudoCodeStrategy> insertRegistry = new HashMap<>();
    private static final Map<String, PseudoCodeStrategy> deleteRegistry = new HashMap<>();
    private static final Map<String, PseudoCodeStrategy> traversalRegistry = new HashMap<>();

    // Strategy rỗng (Null Object Pattern) để tránh lỗi null pointer khi không tìm thấy key
    private static final PseudoCodeStrategy EMPTY_STRATEGY = new PseudoCodeStrategy() {
        @Override public String getTitle() { return ""; }
        @Override public List<String> getLines() { return Collections.emptyList(); }
    };
/*
getOrDefault() là một phương thức trong Java dùng để lấy giá trị tương ứng với một key trong Map ;
 nếu không tìm thấy key đó, nó sẽ trả về một giá trị mặc định mà bạn chỉ định, thay vì báo lỗi,
 giúp code ngắn gọn và xử lý thiếu khóa hiệu quả hơn.
 */
    static {
        insertRegistry.put("Red Black Tree",       new RedBlackTreeInsert());
        insertRegistry.put("Binary Search Tree",   new BSTInsert());
        insertRegistry.put("Binary Tree (Normal)", new BTInsert());

        deleteRegistry.put("Red Black Tree",       new RBTDeleteStrategy());
        deleteRegistry.put("Binary Search Tree",   new BSTDeleteStrategy());
        deleteRegistry.put("Binary Tree (Normal)", new BSTDeleteStrategy());

        traversalRegistry.put("In-Order (LNR)",      new InOrderPCode());
        traversalRegistry.put("Pre-Order (NLR)",     new PreOrderPCode());
        traversalRegistry.put("Post-Order (LRN)",    new PostOrderPCode());
        traversalRegistry.put("BFS (Level Order)",   new BFSPCode());
    }

    public static PseudoCodeStrategy getInsertStrategy(String treeType) {
        return insertRegistry.getOrDefault(treeType, EMPTY_STRATEGY);
    }

    public static PseudoCodeStrategy getDeleteStrategy(String treeType) {
        return deleteRegistry.getOrDefault(treeType, EMPTY_STRATEGY);
    }

    public static PseudoCodeStrategy getTraversalStrategy(String traversalType) {
        return traversalRegistry.getOrDefault(traversalType, EMPTY_STRATEGY);
    }

    public static void registerInsert(String name, PseudoCodeStrategy strategy) {
        insertRegistry.put(name, strategy);
    }

    public static void registerDelete(String name, PseudoCodeStrategy strategy) {
        deleteRegistry.put(name, strategy);
    }

    public static void registerTraversal(String name, PseudoCodeStrategy strategy) { traversalRegistry.put(name, strategy); }
}