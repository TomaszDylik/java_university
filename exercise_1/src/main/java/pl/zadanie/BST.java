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
        // TODO: implementacja w Etapie 3
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
        // TODO: implementacja w Etapie 3
        return false;
    }

    /**
     * Sprawdza, czy drzewo jest puste.
     */
    public boolean isEmpty() {
        return root == null;
    }
}
