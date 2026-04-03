package pl.zadanie;

/**
 * Węzeł drzewa BST przechowujący wartość całkowitą
 * oraz referencje do lewego i prawego poddrzewa.
 */
class BSTNode {

    int value;
    BSTNode left;
    BSTNode right;

    BSTNode(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}
