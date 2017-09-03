package logic;

public class Tree {

    private Node root;

    public Tree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(Node parent, Node node) {
        if (parent == null) {
            root = node;
        } else {
            node.setParent(parent);
            if (parent.getLeftChild() == null) {
                parent.setChildCount(parent.getChildCount() + 1);
                parent.setLeftChild(node);
            } else {
                parent.setChildCount(parent.getChildCount() + 1);
                parent.setRightChild(node);
            }
        }
    }

    /* Function to print level order traversal a tree*/
    public void printLevelOrder(Node r) {
        int h = height(r);
        for (int i = 1; i <= h; i++) {
            printGivenLevel(r, i);
        }
    }

    /* Print nodes at a given level */
    public void printGivenLevel(Node r, int level) {
        if (r == null) {
            return;
        }
        if (level == 1) {
            System.out.println(r.getPremise() + " => " + r.getConclusion());
        } else if (level > 1) {
            printGivenLevel(r.getLeftChild(), level - 1);
            printGivenLevel(root.getRightChild(), level - 1);
        }
    }

    public int height(Node node) {
        if (node == null) {
            return 0;
        } else {
            /* compute the height of each subtree */
            int lheight = height(node.getLeftChild());
            int rheight = height(node.getRightChild());

            /* use the larger one */
            if (lheight > rheight) {
                return (lheight + 1);
            } else {
                return (rheight + 1);
            }
        }
    }
}
