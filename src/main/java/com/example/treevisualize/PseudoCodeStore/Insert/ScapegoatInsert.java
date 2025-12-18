package com.example.treevisualize.PseudoCodeStore.Insert;

import com.example.treevisualize.PseudoCodeStore.PseudoCodeStrategy;
import java.util.Arrays;
import java.util.List;

public class ScapegoatInsert implements PseudoCodeStrategy {
    @Override
    public String getTitle() {
        return "Scapegoat-Insert(val)";
    }

    @Override
    public List<String> getLines() {
        return Arrays.asList(
            "1. Thực hiện BST Insert thông thường",
            "2. Tăng kích thước cây (n++) và q = max(n, q)",
            "3. Nếu depth > log_{1/α}(q):",
            "4.   Tìm node 'u' sâu nhất không thỏa mãn: size(child) <= α * size(u)",
            "5.   Rebuild toàn bộ cây con tại 'u' thành cây cân bằng hoàn hảo",
            "6. Cập nhật lại cấu trúc cây trên màn hình"
        );
    }
}