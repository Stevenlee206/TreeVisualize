package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class AVLInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "AVL-Insert(node, value)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1. Thực hiện chèn BST thông thường",
            "2. Cập nhật chiều cao (height) của node",
            "3. Tính hệ số cân bằng (BF = HL - HR)",
            "4. Nếu BF > 1 & val < Left.val: Quay Phải (LL)",
            "5. Nếu BF < -1 & val > Right.val: Quay Trái (RR)",
            "6. Nếu BF > 1 & val > Left.val: Quay Trái-Phải (LR)",
            "7. Nếu BF < -1 & val < Right.val: Quay Phải-Trái (RL)"
        );
    }
}