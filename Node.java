package logic;

public class Node {

    private final String premise;
    private final String conclusion;
    private boolean leftFunction;
    private boolean rightFuncion;
    private String function;
    private Node parent;
    private Node leftChild;
    private Node rightChild;
    private int childCount;
    private int X;
    private int Y;
    private int rightX;

    public Node(String premise, String conclusion) {
        this.premise = premise;
        this.conclusion = conclusion;
        leftFunction = false;
        rightFuncion = false;
        function = null;
        parent = null;
        leftChild = null;
        rightChild = null;
        X = 0;
        Y = 0;
        childCount = 0;
        rightX = 0;
    }

    public String getPremise() {
        return premise;
    }

    public String getConclusion() {
        return conclusion;
    }

    public String getFunction() {
        return function;
    }

    public boolean getLeftFunction() {
        return leftFunction;
    }

    public boolean getRightFunction() {
        return rightFuncion;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

    public Node getParent() {
        return parent;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getRightX() {
        return rightX;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setLeftFunction(boolean leftFunction) {
        this.leftFunction = leftFunction;
    }

    public void setRightFuncion(boolean rightFuncion) {
        this.rightFuncion = rightFuncion;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setX(int X) {
        this.X = X;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public String print() {
        return premise + " ⇒ " + conclusion;
    }
}
