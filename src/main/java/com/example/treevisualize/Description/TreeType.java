package com.example.treevisualize.Description;

import com.example.treevisualize.PseudoCodeStore.Delete.*;
import com.example.treevisualize.PseudoCodeStore.Insert.*;
import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import com.example.treevisualize.Trees.*;
import com.example.treevisualize.Visualizer.AVLTreeRenderer;
import com.example.treevisualize.Visualizer.BinaryTreeRenderer;
import com.example.treevisualize.Visualizer.GeneralTreeRenderer;
import com.example.treevisualize.Visualizer.TreeRenderer;

import java.util.function.Supplier;

public enum TreeType {
    // 1. Thêm tham số Insert/Delete Strategy vào từng dòng Enum
    RED_BLACK(
            "Red Black Tree", "/images/RBT_icon.png", false,
            new RBTDescription(),
            new RedBlackTreeInsert(), new RBTDeleteStrategy(),
            new BinaryTreeRenderer(),
            RedBlackTree::new
    ),
    BST(
            "Binary Search Tree", "/images/BST_icon.png", false,
            new BSTDescription(),
            new BSTInsert(), new BSTDeleteStrategy(),
            new BinaryTreeRenderer(),
            BinarySearchTree::new
    ),
    BINARY(
            "Binary Tree (Normal)", "/images/BT_icon.png", true,
            new BinaryTreeDescription(),
            new BTInsert(), new BSTDeleteStrategy(),
            new BinaryTreeRenderer(),
            BinaryTree::new
    ),
    GENERAL(
            "General Tree", "/images/GT_icon.png", true,
            new GeneralTreeDescription(),
            new GeneralTreeInsert(), new GTDeleteStrategy(),
            new GeneralTreeRenderer(),
            GeneralTree::new
    ),
    SPLAY(
    "Splay Tree","/images/Splay_icon.png",false,
            new SplayTreeDescription(),
    new SplayInsertStrategy(), // <--- Dùng cái này để hiện mã giả chuẩn Splay
    new SplayDeleteStrategy(),   // Tạm chấp nhận, hoặc tạo thêm SplayDeleteStrategy
    new BinaryTreeRenderer(),
    SplayTree::new),
    AVL("AVL Tree", "/images/AVL_icon.png",false,
            new AVLDescription(),
            new AVLInsert(),new AVLDeleteStrategy(),
            new AVLTreeRenderer(),
            AVLTree::new);

    // --- CÁC TRƯỜNG DỮ LIỆU ---
    private final String displayName;
    private final String iconPath;
    private final boolean requiresParentInput;
    private final Description descriptionStrategy;
    private final TreeRenderer renderer;
    private final PseudoCodeStrategy insertStrategy;
    private final PseudoCodeStrategy deleteStrategy;
    private final Supplier<Tree> treeFactory;

    // --- CONSTRUCTOR ---
    TreeType(String name, String icon, boolean reqParent,
             Description desc,
             PseudoCodeStrategy insert, PseudoCodeStrategy delete,TreeRenderer renderer,Supplier<Tree> factory) { // Thêm tham số
        this.displayName = name;
        this.iconPath = icon;
        this.requiresParentInput = reqParent;
        this.descriptionStrategy = desc;
        this.insertStrategy = insert;
        this.deleteStrategy = delete;
        this.renderer = renderer;
        this.treeFactory = factory;
    }

    // --- GETTERS ---
    public String getDisplayName() { return displayName; }
    public String getIconPath() { return iconPath; }
    public boolean isRequiresParentInput() { return requiresParentInput; }
    public String getDescriptionText() { return descriptionStrategy.getDescription(); }

    // [MỚI] Getter cho Strategy
    public PseudoCodeStrategy getInsertStrategy() { return insertStrategy; }
    public PseudoCodeStrategy getDeleteStrategy() { return deleteStrategy; }
    public TreeRenderer getRenderer() { return renderer; }
    public Tree createTreeInstance() {
        return treeFactory.get(); // Tạo ra một object Tree mới (new BinaryTree()...)
    }
}