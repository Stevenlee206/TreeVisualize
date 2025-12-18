package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class AVLDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "AVL-Delete(root, val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
                "1. Thực hiện xóa như BST thông thường",
                "2. Cập nhật Height của node hiện tại",
                "3. Tính Balance Factor (BF = HeightL - HeightR)",
                "4. Nếu BF > 1 và BF(Trái) >= 0: Xoay Phải (LL)",
                "5. Nếu BF > 1 và BF(Trái) < 0: Xoay Trái-Phải (LR)",
                "6. Nếu BF < -1 và BF(Phải) <= 0: Xoay Trái (RR)",
                "7. Nếu BF < -1 và BF(Phải) > 0: Xoay Phải-Trái (RL)"
        );
    }
}