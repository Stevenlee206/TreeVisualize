package com.example.treevisualize.Model.Description;

import com.example.treevisualize.Controller.Operators.Delete.Deleter;
import com.example.treevisualize.Controller.Operators.Insert.Inserter;
import com.example.treevisualize.Controller.Operators.Search.Searcher;
import com.example.treevisualize.Model.Description.Profiles.TreeProfile;
import com.example.treevisualize.Model.Description.Profiles.Concrete.*; // Import các Profile
import com.example.treevisualize.View.Layout.Strategy.NodeAlignmentStrategy;
import com.example.treevisualize.Model.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Model.Tree.Tree;
import com.example.treevisualize.View.Visualizer.TreeRenderer;

public enum TreeType {

    // Khai báo cực ngắn gọn
    RED_BLACK(new RedBlackTreeProfile()),
    BST(new BSTProfile()),
    BINARY(new NormalBinaryTreeProfile()),
    GENERAL(new GeneralTreeProfile()),
    SPLAY(new SplayTreeProfile()),
    AVL(new AVLTreeProfile()),
    SCAPEGOAT(new ScapegoatTreeProfile());

    ;

    // Chỉ giữ đúng 1 trường duy nhất!
    private final TreeProfile profile;

    // Constructor đơn giản
    TreeType(TreeProfile profile) {
        this.profile = profile;
    }

    // --- DELEGATE METHODS (Ủy quyền) ---
    // Các class bên ngoài (Visualizer, Controller) vẫn gọi hàm get như cũ,
    // nhưng Enum sẽ chuyển lời gọi đó sang các Module con.

    // 1. Nhóm Metadata
    public String getDisplayName() { return profile.getMetadata().getDisplayName(); }
    public String getIconPath() { return profile.getMetadata().getIconPath(); }
    public boolean isRequiresParentInput() { return profile.getMetadata().isRequiresParentInput(); }

    // 2. Nhóm Operations
    public Tree createTreeInstance() { return profile.getOperations().createTree(); }
    public Inserter getInserter() { return profile.getOperations().getInserter(); }
    public Deleter getDeleter() { return profile.getOperations().getDeleter(); }
    public Searcher getSearcher() { return profile.getOperations().getSearcher(); }

    // 3. Nhóm Presentation
    public TreeRenderer getRenderer() { return profile.getPresentation().getRenderer(); }
    public NodeAlignmentStrategy getAlignmentStrategy() { return profile.getPresentation().getLayoutStrategy(); }
    public String getDescriptionText() { return profile.getPresentation().getDescriptionText(); }
    public PseudoCodeStrategy getInsertStrategy() { return profile.getPresentation().getInsertCode(); }
    public PseudoCodeStrategy getDeleteStrategy() { return profile.getPresentation().getDeleteCode(); }
    public PseudoCodeStrategy getSearchStrategy() {
        // Lưu ý: Bạn cần chắc chắn class TreePresentation (trong Profile)
        // đã có hàm getSearchCode() trả về (ví dụ) BSTSearch strategy.
        return profile.getPresentation().getSearchCode();
    }
}