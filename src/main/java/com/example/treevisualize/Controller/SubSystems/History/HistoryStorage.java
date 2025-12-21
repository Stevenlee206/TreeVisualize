package com.example.treevisualize.Controller.SubSystems.History;

import com.example.treevisualize.View.Visualizer.TreeSnapShot;
import java.util.List;

public interface HistoryStorage {
    // Nhập kho phim mới
    void save(List<TreeSnapShot> frames);

    // Kiểm tra chất lượng phim (chặn lỗi 1 frame)
    boolean isValid();

    // Lấy frame hiện tại
    TreeSnapShot getCurrent();

    // Điều hướng
    boolean hasNext();
    boolean hasPrev();

    TreeSnapShot next(); // Tiến và trả về frame mới
    TreeSnapShot prev(); // Lùi và trả về frame mới

    void reset(); // Tua về đầu
}