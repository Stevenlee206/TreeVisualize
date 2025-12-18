package com.example.treevisualize.Description;

public class AVLDescription implements Description {
    @Override
    public String getDescription() {
        return "CÂY AVL (Adelson-Velsky and Landi)\n" +
               "----------------------------------\n" +
               "ĐỊNH NGHĨA: Là cây BST tự cân bằng. Độ lệch chiều cao của 2 cây con không quá 1.\n" +
               "ĐỘ PHỨC TẠP: O(log N) cho mọi thao tác.\n" +
               "CƠ CHẾ: Sử dụng các phép quay (Rotations) để tái cân bằng sau mỗi lần chèn/xóa.";
    }
}