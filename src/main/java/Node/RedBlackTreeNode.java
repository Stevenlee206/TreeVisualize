package Node;

public class RedBlackTreeNode extends BinaryTreeNode {
       private NodeColor  color;

    public RedBlackTreeNode(int value) {
        super(value);
        this.color = NodeColor.RED;
    }

    public void changeColor(NodeColor color) {
        this.color = color;
    }

    public NodeColor getColor() {
        return color;
    }
    public boolean isRed() {
        return this.color == NodeColor.RED;
    }
    public void flipColor() {
        if (this.color == NodeColor.RED) {
            this.color = NodeColor.BLACK;
        } else {
            this.color = NodeColor.RED;
        }
    }
    @Override
    public Node copy() {
        // 1. Tạo vỏ mới đúng kiểu RedBlack
        RedBlackTreeNode newNode = new RedBlackTreeNode(this.getValue());

        // 2. Copy thuộc tính riêng (Màu sắc)
        newNode.changeColor(this.color);

        // 3. Copy con (Logic giống BinaryTreeNode nhưng phải viết lại để gắn vào newNode này)
        if (this.getLeftChild() != null) {
            newNode.setLeftChild((BinaryTreeNode) this.getLeftChild().copy());
        }
        if (this.getRightChild() != null) {
            newNode.setRightChild((BinaryTreeNode) this.getRightChild().copy());
        }

        return newNode;
    }
}
