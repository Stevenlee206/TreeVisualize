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
}
