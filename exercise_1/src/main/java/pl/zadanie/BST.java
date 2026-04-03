package pl.zadanie;

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
     * Przeszukiwanie wszerz (BFS) — zwraca elementy poziom po poziomie.
     * Używa wewnętrznie klasy Queue<BSTNode>.
     */
    public List<Integer> bfs() {
        // TODO: implementacja w Etapie 5
        return List.of();
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
