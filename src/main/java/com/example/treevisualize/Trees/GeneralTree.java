package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.Node;
import com.example.treevisualize.Node.GeneralTreeNode;
public class GeneralTree extends Tree {
    public GeneralTree(){
        super();
    }
    public void insert(int parentVal, int childVal) {
        GeneralTreeNode parent = (GeneralTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Không tìm thấy node cha có giá trị: " + parentVal);
        } else {
            parent.addChild(new GeneralTreeNode(childVal));
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
        GeneralTreeNode targetNode = (GeneralTreeNode) search(value);

        if (targetNode == null) {
            notifyError("Không tìm thấy giá trị " + value + " để xóa.");
            return;
        }
        if (targetNode == root) {
            clear();
            notifyStructureChanged();
            return;
        }

        GeneralTreeNode parent = (GeneralTreeNode) targetNode.getParent();

        if (parent != null) {
            parent.removeChild(new GeneralTreeNode(value));
            notifyStructureChanged();
        } else {
            notifyError("Lỗi cấu trúc: com.example.treevisualize.Node " + value + " bị mất liên kết với cha.");
        }
    }

    private GeneralTreeNode searchRecursive(GeneralTreeNode node, int value) {
        if (node == null) return null;

        if (node.getValue() == value) {
            return node;
        }

        GeneralTreeNode foundInChild = searchRecursive(node.getLeftMostChild(), value);
        if (foundInChild != null) {
            return foundInChild;
        }

        return searchRecursive(node.getRightSibling(), value);
        }

    @Override
    public void insert(int value) {
        if (root == null) {
            root = new GeneralTreeNode(value); // OK: Tạo gốc
            notifyStructureChanged();
        } else {
            // Thay vì chỉ báo lỗi, có thể chọn mặc định chèn vào con đầu tiên?
            // Hoặc giữ nguyên báo lỗi nhưng ném Exception rõ ràng thay vì notifyError string.
            throw new UnsupportedOperationException("General Tree insert requires Parent ID. Use insert(parent, child) instead.");
        }
    }
}
