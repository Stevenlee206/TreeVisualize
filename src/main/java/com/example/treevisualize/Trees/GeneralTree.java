package com.example.treevisualize.Trees;

import com.example.treevisualize.Node.GeneralTreeNode;
import com.example.treevisualize.Visualizer.Events.StandardEvent; // Import

public class GeneralTree extends Tree {

    public GeneralTree(){
        super();
    }

    // Insert kiểu cũ (chỉ nhận value) -> Báo lỗi hoặc hỗ trợ root
    @Override
    public void insert(int value) {
        notifyEvent(StandardEvent.START, root);
        if (root == null) {
            notifyEvent(StandardEvent.CHECK_ROOT_EMPTY, null);
            root = new GeneralTreeNode(value);
            notifyEvent(StandardEvent.INSERT_SUCCESS, root);
            notifyStructureChanged();
        } else {
            // General Tree cần parent, ném lỗi rõ ràng
            throw new UnsupportedOperationException("General Tree insert requires Parent ID. Use insert(parent, child).");
        }
    }

    // Insert kiểu mới (parent, child)
    public void insert(int parentVal, int childVal) {
        notifyEvent(StandardEvent.START, root);

        // Tìm cha
        GeneralTreeNode parent = (GeneralTreeNode) search(parentVal);

        if (parent == null) {
            notifyError("Cannot find parent node with value: " + parentVal);
        } else {
            notifyEvent(StandardEvent.FOUND_INSERT_POS, parent); // Tìm thấy cha
            parent.addChild(new GeneralTreeNode(childVal));
            notifyEvent(StandardEvent.INSERT_SUCCESS, null);
            notifyStructureChanged();
        }
    }

    // --- Search ---
    @Override
    public com.example.treevisualize.Node.Node search(int value){
        if (root == null) return null;
        return searchRecursive((GeneralTreeNode) root, value);
    }

    private GeneralTreeNode searchRecursive(GeneralTreeNode node, int value) {
        if (node == null) return null;
        if (node.getValue() == value) return node;

        // Đệ quy con và anh em (Logic đặc thù General Tree)
        GeneralTreeNode foundInChild = searchRecursive(node.getLeftMostChild(), value);
        if (foundInChild != null) return foundInChild;

        return searchRecursive(node.getRightSibling(), value);
    }

    // --- Delete ---
    @Override
    public void delete(int value) {
        notifyEvent(StandardEvent.DELETE_START, root);

        if (root == null) return;
        GeneralTreeNode targetNode = (GeneralTreeNode) search(value);

        if (targetNode == null) {
            notifyError("Cannot delete: value " + value + " not found.");
            return;
        }

        notifyEvent(StandardEvent.DELETE_SUCCESS, targetNode);

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
            notifyError("Structural error: Node " + value + " lost connection to parent.");
        }
    }
}