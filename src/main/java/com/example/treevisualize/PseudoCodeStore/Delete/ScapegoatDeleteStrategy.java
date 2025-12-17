package com.example.treevisualize.PseudoCodeStore.Delete;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class ScapegoatDeleteStrategy implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Scapegoat-Delete(val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1. Thực hiện BST Delete thông thường",
            "2. Giảm kích thước cây (n--)",
            "3. Nếu n < α * q:",
            "4.   Rebuild lại toàn bộ cây từ gốc (root)",
            "5.   Cập nhật q = n",
            "6. Thông báo thay đổi cấu trúc cây"
        );
    }
}