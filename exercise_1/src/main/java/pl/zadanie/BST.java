package pl.zadanie;

import java.util.ArrayList;
import java.util.List;

/**
 * Binarne drzewo wyszukiwań (BST).
 * Wewnętrznie węzły są klasy BSTNode.
 */
public class BST {

    BSTNode root;

    public BST() {
        this.root = null;
    }

    /**
     * Wstawia klucz do drzewa. Duplikaty są ignorowane.
     */
    public void insert(int key) {
        root = insertNode(root, key);
    }

    private BSTNode insertNode(BSTNode node, int key) {
        if (node == null) {
            return new BSTNode(key);
        }
        if (key < node.value) {
            node.left = insertNode(node.left, key);
        } else if (key > node.value) {
            node.right = insertNode(node.right, key);
        }
        // key == node.value: duplikat — ignorujemy
        return node;
    }

    /**
     * Usuwa węzeł o podanym kluczu z drzewa.
     */
    public void delete(int key) {
        // TODO: implementacja w Etapie 4
    }

    /**
     * Przeszukiwanie wszerz (BFS)
     */
    public List<Integer> bfs() {
        List<Integer> result = new ArrayList<>();
        if (isEmpty()) {
            return result;
        }
        Queue<BSTNode> queue = new Queue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            BSTNode current = queue.dequeue();
            result.add(current.value);
            if (current.left != null) {
                queue.enqueue(current.left);
            }
            if (current.right != null) {
                queue.enqueue(current.right);
            }
        }
        return result;
    }

    /**
     * Sprawdza, czy klucz istnieje w drzewie.
     */
    public boolean contains(int key) {
        return containsNode(root, key);
    }

    private boolean containsNode(BSTNode node, int key) {
        if (node == null) {
            return false;
        }
        if (key == node.value) {
            return true;
        }
        if (key < node.value) {
            return containsNode(node.left, key);
        }
        return containsNode(node.right, key);
    }

    /**
     * Sprawdza, czy drzewo jest puste.
     */
    public boolean isEmpty() {
        return root == null;
    }
}
