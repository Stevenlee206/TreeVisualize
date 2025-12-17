package com.example.treevisualize.Description;

public class ScapegoatDescription implements Description {
    @Override
    public String getDescription() {
        return "CÂY SCAPEGOAT (VẬT TẾ THẦN)\n" +
               "----------------------------------\n" +
               "ĐỊNH NGHĨA:\n" +
               "Là một cây tìm kiếm nhị phân (BST) tự cân bằng dựa trên kích thước (weight-balanced). " +
               "Điểm đặc biệt là nó không cần lưu trữ thêm thông tin (như màu sắc hay chiều cao) tại mỗi node.\n\n" +
               "CƠ CHẾ CÂN BẰNG:\n" +
               "Cây sử dụng một hệ số alpha (thường là 0.7). Khi một node vi phạm điều kiện cân bằng alpha " +
               "(độ sâu vượt quá giới hạn cho phép), thuật toán sẽ tìm node 'vật tế thần' (Scapegoat) " +
               "đầu tiên phía trên nó và đập đi xây lại (rebuild) toàn bộ cây con đó thành một cây cân bằng hoàn hảo.\n\n" +
               "ĐỘ PHỨC TẠP:\n" +
               "• Tìm kiếm: O(log n)\n" +
               "• Chèn/Xóa: O(log n) (chi phí khấu hao)\n\n" +
               "ƯU ĐIỂM:\n" +
               "Tiết kiệm bộ nhớ vì không cần biến phụ tại node. Rất hiệu quả khi thao tác tìm kiếm chiếm đa số.";
    }
}