package Node;

public class BinaryTreeNode extends Node{
    private BinaryTreeNode leftChild;
    private BinaryTreeNode rightChild;

    public BinaryTreeNode(int value) {
        super(value);
        this.leftChild = null;
        this.rightChild = null;
    }

    public BinaryTreeNode getLeftChild() {
        return leftChild;
    }

    public BinaryTreeNode getRightChild() {
        return rightChild;
    }
    // Update from gemini , check later
    public void setLeftChild(BinaryTreeNode child) {
        this.leftChild = child;
        if (child != null) {
            child.setParent(this);
        }
    }

    public void setRightChild(BinaryTreeNode child) {
        this.rightChild = child;
        if (child != null) {
            child.setParent(this);
        }
    }
    //-------------------------------------------------------
    public void addChild(BinaryTreeNode child) throws FullNodeException {
        if (child==null){ return; }
        if (this.leftChild == null) {
            this.setLeftChild(child);
            return;
        }
        if (this.rightChild == null) {
            this.setRightChild(child);
            return;
        } throw new FullNodeException("Node "+this.getValue()+" is full.");
    }

    public void removeChild(BinaryTreeNode child) throws NotFoundException {
        if (child == null) return;
        if (this.leftChild != null && this.leftChild.getValue() == child.getValue()) {
            this.leftChild.setParent(null);
            this.leftChild = null;
            return;
        }

        if (this.rightChild != null && this.rightChild.getValue() == child.getValue()) {
            this.rightChild.setParent(null);
            this.rightChild = null;
            return;
        }
        throw new NotFoundException("Node " + this.getValue() + " do not have child with value " + child.getValue());
    }

    @Override
    public Node copy() {
        // 1. Tạo vỏ mới
        BinaryTreeNode newNode = new BinaryTreeNode(this.getValue());

        // 2. Copy con Trái (Đệ quy)
        if (this.leftChild != null) {
            // Gọi copy() của con, sau đó ép kiểu và gán
            newNode.setLeftChild((BinaryTreeNode) this.leftChild.copy());
        }

        // 3. Copy con Phải (Đệ quy)
        if (this.rightChild != null) {
            newNode.setRightChild((BinaryTreeNode) this.rightChild.copy());
        }

        return newNode;
    }
}
