package pl.zadanie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Testy opisujące zachowanie BST
 */
public class BSTTest {

    /** Nowo utworzone drzewo powinno być puste. */
    @Test
    public void newBSTShouldBeEmpty() {
        BST bst = new BST();
        assertTrue(bst.isEmpty());
        assertEquals(List.of(), bst.bfs());
    }

    /** Po wstawieniu elementu drzewo nie powinno być puste. */
    @Test
    public void shouldNotBeEmptyAfterInsert() {
        BST bst = new BST();

        bst.insert(5);

        assertFalse(bst.isEmpty(), "Drzewo nie powinno być puste po wstawieniu elementu");
    }

    /** Wstawiony element powinien być odnajdywany w drzewie. */
    @Test
    public void shouldContainInsertedElement() {
        BST bst = new BST();

        bst.insert(10);

        assertTrue(bst.contains(10), "Drzewo powinno zawierać wstawiony element");
    }

    /** Element nieistniejący nie powinien być znajdowany. */
    @Test
    public void shouldNotContainAbsentElement() {
        BST bst = new BST();
        bst.insert(10);

        assertFalse(bst.contains(99), "Drzewo nie powinno zawierać elementu, który nie był wstawiony");
    }

    /** Element mniejszy od korzenia powinien trafić w lewe poddrzewo. */
    @Test
    public void smallerElementShouldGoLeft() {
        BST bst = new BST();
        bst.insert(5);
        bst.insert(3);

        assertNotNull(bst.root.left, "Mniejszy element powinien być lewym dzieckiem korzenia");
        assertEquals(3, bst.root.left.value);
    }

    /** Element większy od korzenia powinien trafić w prawe poddrzewo. */
    @Test
    public void largerElementShouldGoRight() {
        BST bst = new BST();
        bst.insert(5);
        bst.insert(7);

        assertNotNull(bst.root.right, "Większy element powinien być prawym dzieckiem korzenia");
        assertEquals(7, bst.root.right.value);
    }

    /** Wstawienie kilku elementów — BFS powinien zwrócić je poziom po poziomie. */
    @Test
    public void bfsShouldReturnElementsLevelByLevel() {
        BST bst = new BST();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);

        // Poziom 0: [5], poziom 1: [3, 7]
        assertEquals(List.of(5, 3, 7), bst.bfs());
    }

    /** Duplikaty nie powinny być wstawiane. */
    @Test
    public void shouldIgnoreDuplicates() {
        BST bst = new BST();
        bst.insert(5);
        bst.insert(5);

        assertEquals(List.of(5), bst.bfs(), "Duplikaty nie powinny być dodawane do drzewa");
    }

    /** BFS dla większego drzewa — kolejność poziomów. */
    @Test
    public void bfsShouldReturnCorrectOrderForComplexTree() {
        BST bst = new BST();
        // Struktura:
        //        5
        //       / \
        //      3   7
        //     / \
        //    1   4
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(1);
        bst.insert(4);

        assertEquals(List.of(5, 3, 7, 1, 4), bst.bfs());
    }

    /** Usunięcie liścia. */
    @Test
    public void shouldDeleteLeafNode() {
        BST bst = new BST();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);

        bst.delete(3);

        assertFalse(bst.contains(3), "Usunięty liść nie powinien być w drzewie");
        assertEquals(List.of(5, 7), bst.bfs());
    }

    /** Usunięcie węzła z jednym dzieckiem. */
    @Test
    public void shouldDeleteNodeWithOneChild() {
        BST bst = new BST();
        //   5
        //  /
        // 3
        //  \
        //   4
        bst.insert(5);
        bst.insert(3);
        bst.insert(4);

        bst.delete(3);

        assertFalse(bst.contains(3), "Usunięty węzeł nie powinien być w drzewie");
        assertTrue(bst.contains(4), "Dziecko usuniętego węzła powinno pozostać");
        assertEquals(List.of(5, 4), bst.bfs());
    }

    /** Usunięcie węzła z dwojgiem dzieci — zastępowany następnikiem in-order. */
    @Test
    public void shouldDeleteNodeWithTwoChildren() {
        BST bst = new BST();
        //      5
        //     / \
        //    3   7
        //   / \
        //  1   4
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(1);
        bst.insert(4);

        bst.delete(3);

        assertFalse(bst.contains(3), "Usunięty węzeł nie powinien być w drzewie");
        assertTrue(bst.contains(1), "Lewe dziecko powinno pozostać");
        assertTrue(bst.contains(4), "Prawe dziecko powinno pozostać");
        assertTrue(bst.contains(5), "Korzeń powinien pozostać");
    }

    /** Usunięcie korzenia. */
    @Test
    public void shouldDeleteRoot() {
        BST bst = new BST();
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);

        bst.delete(5);

        assertFalse(bst.contains(5), "Korzeń powinien zostać usunięty");
        assertTrue(bst.contains(3));
        assertTrue(bst.contains(7));
    }

    /** Usunięcie nieistniejącego klucza nie powinno rzucać wyjątku. */
    @Test
    public void shouldNotThrowWhenDeletingAbsentKey() {
        BST bst = new BST();
        bst.insert(5);

        assertDoesNotThrow(() -> bst.delete(99));
        assertTrue(bst.contains(5), "Pozostałe elementy powinny być nienaruszone");
    }
}
