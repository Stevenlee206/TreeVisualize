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
}
