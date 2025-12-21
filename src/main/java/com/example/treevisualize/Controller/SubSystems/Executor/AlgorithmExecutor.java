package com.example.treevisualize.Controller.SubSystems.Executor;

import com.example.treevisualize.Controller.Operators.Delete.Deleter;
import com.example.treevisualize.Controller.Operators.Insert.Inserter;
import com.example.treevisualize.Controller.Operators.Search.Searcher;
import com.example.treevisualize.Model.Description.TreeType;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Shared.TraversalType;
import com.example.treevisualize.Model.Tree.Tree;
import com.example.treevisualize.View.Visualizer.ExecutionMonitor;
import com.example.treevisualize.View.Visualizer.PseudoCodeBlock;

public class AlgorithmExecutor {
    private final Tree tree;
    private final PseudoCodeBlock pseudoCode;
    private final Inserter inserter;
    private final Deleter deleter;
    private final Searcher searcher;
    private final TreeType treeType;

    public AlgorithmExecutor(Tree tree, TreeType type, PseudoCodeBlock pseudoCode) {
        this.tree = tree;
        this.pseudoCode = pseudoCode;
        this.treeType = type;
        // Lấy nhân viên từ cấu hình Enum
        this.inserter = type.getInserter();
        this.deleter = type.getDeleter();
        this.searcher = type.getSearcher();
    }

    public void executeInsert(int val) {
        // 1. Chuẩn bị: Hiển thị code giả & Gắn máy nghe lén (Monitor)
        prepare(treeType.getInsertStrategy());
        try {
            // 2. Thực thi: Gọi nhân viên cũ làm việc (Không sửa logic Inserter)
            inserter.insert(tree, val);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 3. Dọn dẹp: Gỡ máy nghe lén
            cleanup();
        }
    }
    public void executeInsert(int p, int c) {
        prepare(treeType.getInsertStrategy());
        try {
            inserter.insert(tree, p, c);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
    public void executeDelete(int val) {
        prepare(treeType.getDeleteStrategy());
        try {
            deleter.delete(tree, val);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
    public void executeSearch(int val) {
        prepare(treeType.getSearchStrategy());
        try {
            searcher.search(tree, val);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    public void executeTraversal(TraversalType type) {
        // Traversal hơi đặc biệt, strategy lấy từ tham số type
        prepare(type.getPseudoCode());
        try {
            tree.resetTreeStatus();
            if (type.getAlgorithm() != null) {
                tree.traverse(type.getAlgorithm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }

    public Tree getTree() {
        return tree;
    }

    private void prepare(PseudoCodeStrategy strategy) {
        if (strategy == null) return;

        // 1. Update UI (Hiển thị text code giả)
        if (pseudoCode != null) {
            pseudoCode.setCode(strategy.getTitle(), strategy.getLines());
        }

        // 2. Gắn Monitor vào cây
        // Khi cây bắn Event -> Monitor dịch ra số dòng -> Báo lại cây -> Cây báo Recorder
        ExecutionMonitor monitor = (event, node) -> {
            int line = strategy.getLineIndex(event);
            if (line != -1) {
                tree.updatePseudoStep(line);
            }
        };

        if (tree instanceof Tree) {
            // Nếu bạn dùng AbstractTree hoặc class Tree của bạn có method setMonitor
            tree.setMonitor(monitor);
        } else {
            // Fallback nếu Tree của bạn không kế thừa AbstractTree mà tự implement setMonitor
            tree.setMonitor(monitor);
        }
    }

    private void cleanup() {
        tree.setMonitor(null);
    }
}
