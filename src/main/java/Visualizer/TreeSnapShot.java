package Visualizer;

import Node.Node;

public class TreeSnapShot {
    private Node rootCopy;
    private int pseudoLineIndex;
    private String statusMessage;

    public Node getRootCopy() {
        return rootCopy;
    }

    public int getPseudoLineIndex() {
        return pseudoLineIndex;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public TreeSnapShot(Node realRoot,int lineIdx,String message){
        this.rootCopy = deepCopy(realRoot);
        this.pseudoLineIndex = lineIdx;
        this.statusMessage = message;
    }

    /**
     * Hàm đệ quy để tạo ra một bản sao hoàn chỉnh của cây.
     * Tự động nhận diện loại Node (RedBlack, Binary, General) để copy đúng cách.
     */
    private Node deepCopy(Node node) {
        if (node == null) {
            return null;
        }

        // 1. XỬ LÝ RED-BLACK TREE NODE
        if (node instanceof RedBlackTreeNode) {
            RedBlackTreeNode original = (RedBlackTreeNode) node;
            RedBlackTreeNode copy = new RedBlackTreeNode(original.getValue());

            // Copy màu sắc (Quan trọng nhất của RB Tree)
            copy.setColor(original.getColor());

            // Đệ quy copy con trái và con phải
            copy.setLeftChild((BinaryTreeNode) deepCopy(original.getLeftChild()));
            copy.setRightChild((BinaryTreeNode) deepCopy(original.getRightChild()));

            return copy;
        }

        // 2. XỬ LÝ BINARY TREE NODE THƯỜNG
        else if (node instanceof BinaryTreeNode) {
            BinaryTreeNode original = (BinaryTreeNode) node;
            BinaryTreeNode copy = new BinaryTreeNode(original.getValue());

            // Đệ quy copy con trái và con phải
            copy.setLeftChild((BinaryTreeNode) deepCopy(original.getLeftChild()));
            copy.setRightChild((BinaryTreeNode) deepCopy(original.getRightChild()));

            return copy;
        }

        // 3. XỬ LÝ GENERAL TREE NODE (LCRS)
        else if (node instanceof GeneralTreeNode) {
            GeneralTreeNode original = (GeneralTreeNode) node;
            GeneralTreeNode copy = new GeneralTreeNode(original.getValue());

            // Đệ quy copy con cả
            GeneralTreeNode childCopy = (GeneralTreeNode) deepCopy(original.getLeftMostChild());
            copy.setLeftMostChild(childCopy);
            if (childCopy != null) childCopy.setParent(copy);

            // Đệ quy copy em ruột (Sibling)
            GeneralTreeNode siblingCopy = (GeneralTreeNode) deepCopy(original.getRightSibling());
            copy.setRightSibling(siblingCopy);
            // Lưu ý: Sibling có cùng cha với copy, nhưng ở đây ta chưa biết cha của copy là ai
            // Việc gán parent cho sibling thường do logic bên ngoài hoặc node cha quản lý.

            return copy;
        }

        // 4. NODE CƠ BẢN (Fallback)
        else {
            return new Node(node.getValue()) {}; // Anonymous class nếu Node là abstract
        }
    }
}
