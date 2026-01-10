package com.example.treevisualize.View.Visualizer.Events;

import com.example.treevisualize.View.Visualizer.AlgorithmEvent;

public enum RBTEvent implements AlgorithmEvent {
    // Sự kiện tô màu
    PAINT_RED,
    PAINT_BLACK,

    // Các trường hợp cân bằng (Fixup)
    FIXUP_START,    // Bắt đầu quá trình sửa lỗi
    CASE_1,         // Chú đỏ -> Đổi màu
    CASE_2,         // Chú đen, Node ở vị trí ziczac -> Xoay
    CASE_3,         // Chú đen, Node thẳng hàng -> Đổi màu + Xoay

    // Xoay (Có thể dùng chung AVLEvent hoặc khai báo lại)
    ROTATE_LEFT,
    ROTATE_RIGHT
}