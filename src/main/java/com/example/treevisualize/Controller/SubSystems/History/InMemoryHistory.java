package com.example.treevisualize.Controller.SubSystems.History;

import com.example.treevisualize.View.Visualizer.TreeSnapShot;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistory implements HistoryStorage {
    private List<TreeSnapShot> frames = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    public void save(List<TreeSnapShot> frames) {
        this.frames = frames;
        this.currentIndex = 0;
    }

    @Override
    public boolean isValid() {
        // Một bộ phim hợp lệ phải có > 1 khung hình (Start -> End)
        // Đây là chốt chặn quan trọng để tránh lỗi StackOverflow
        return frames != null && frames.size() > 1;
    }

    @Override
    public TreeSnapShot getCurrent() {
        if (frames.isEmpty()) return null;
        return frames.get(currentIndex);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < frames.size() - 1;
    }

    @Override
    public boolean hasPrev() {
        return currentIndex > 0;
    }

    @Override
    public TreeSnapShot next() {
        if (hasNext()) currentIndex++;
        return getCurrent();
    }

    @Override
    public TreeSnapShot prev() {
        if (hasPrev()) currentIndex--;
        return getCurrent();
    }

    @Override
    public void reset() {
        currentIndex = 0;
    }

    @Override
    public void clear() {
        // Sử dụng tên biến 'frames' thay vì 'buffer'
        if (this.frames != null) { 
            this.frames.clear(); // Xóa sạch danh sách các khung hình
        }
        // Đưa chỉ số về 0 (theo giá trị khởi tạo của bạn)
        this.currentIndex = 0; //
    }
}
