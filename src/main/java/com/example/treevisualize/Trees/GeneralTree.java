package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.GeneralTreeNode;
public class GeneralTree extends Tree {
    public GeneralTree(){
        super();
    }
    public void insert(int parentVal, int childVal) {
        // 1. Tìm node cha
        // search() sẽ trả về node ĐẦU TIÊN tìm thấy nếu có nhiều node trùng giá trị parentVal
        GeneralTreeNode parent = (GeneralTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Không tìm thấy node cha có giá trị: " + parentVal);
        } else {
            // 2. Thực hiện thêm con
            // com.example.treevisualize.Node mới được tạo và nối vào cuối danh sách con của parent
            parent.addChild(new GeneralTreeNode(childVal));

            // 3. Thông báo cập nhật giao diện
            notifyStructureChanged();
        }
    }
    @Override
    public Node search(int value){
        if (root == null) return null;
        return searchRecursive((GeneralTreeNode) root, value);
    }
    @Override
    public void delete(int value) {
        if (root == null) {
            return;
        }

        // 1. Tìm node cần xóa
        // Nếu có nhiều node cùng giá trị, search() sẽ trả về node đầu tiên gặp (node nằm trên/trái nhất)
        GeneralTreeNode targetNode = (GeneralTreeNode) search(value);

        // Nếu không tìm thấy trong cây
        if (targetNode == null) {
            notifyError("Không tìm thấy giá trị " + value + " để xóa.");
            return;
        }

        // 2. Trường hợp xóa Root
        if (targetNode == root) {
            clear(); // Xóa sạch cây
            notifyStructureChanged();
            return;
        }

        // 3. Lấy node cha trực tiếp từ thuộc tính parent
        // (Yêu cầu: class com.example.treevisualize.Node phải có hàm getParent() và đã setParent khi insert)
        GeneralTreeNode parent = (GeneralTreeNode) targetNode.getParent();

        if (parent != null) {
            // Gọi hàm removeChild nhận vào (int value) mà ta đã sửa ở các bước trước
            parent.removeChild(new GeneralTreeNode(value));

            // Thông báo vẽ lại
            notifyStructureChanged();
        } else {
            // Trường hợp lỗi dữ liệu: com.example.treevisualize.Node tồn tại nhưng parent lại null (mà không phải Root)
            notifyError("Lỗi cấu trúc: com.example.treevisualize.Node " + value + " bị mất liên kết với cha.");
        }
    }

    private GeneralTreeNode searchRecursive(GeneralTreeNode node, int value) {
        if (node == null) return null;

        // 1. Tìm thấy tại node hiện tại
        if (node.getValue() == value) {
            return node;
        }

        // 2. Đệ quy tìm trong nhánh con (Left Child)
        GeneralTreeNode foundInChild = searchRecursive(node.getLeftMostChild(), value);
        if (foundInChild != null) {
            return foundInChild;
        }
        // 3. Đệ quy tìm trong nhánh anh em (Right Sibling)
        return searchRecursive(node.getRightSibling(), value);
        }
        @Override
        public void insert(int value) {
            // Nếu cây chưa có gốc -> Tạo gốc
            if (root == null) {
                root = new GeneralTreeNode(value);
                notifyStructureChanged(); // Báo vẽ lại
            } else {
                // Nếu đã có gốc, báo lỗi vì không biết nối vào đâu
                notifyError("Cây tổng quát cần biết Parent ID. Hãy dùng chức năng: Insert(parent, child)");
            }
        }
}
