package pl.zadanie;

/**
 * Prosta kolejka FIFO oparta na liście jednokierunkowej.
 */
public class Queue<A> {

    /** Pierwszy element kolejki (początek listy). */
    private Node<A> head;

    /** Ostatni element kolejki — umożliwia O(1) enqueue. */
    private Node<A> tail;

    /** Węzeł listy używanej wewnętrznie przez kolejkę. */
    private static class Node<A> {
        A value;
        Node<A> next;

        Node(A value) {
            this.value = value;
            this.next = null;
        }
    }

    public Queue() {
        this.head = null;
        this.tail = null;
    }

    /**
     * Sprawdza, czy kolejka nie zawiera elementów.
     */
    public boolean isEmpty() {
        return head == null; 
    }

    /**
     * Dodaje element do kolejki.
     */
    public void enqueue(A value) {
        Node<A> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }
    
    /**
     * Usuwa i zwraca pierwszy element z kolejki.
     */
    public A dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Kolejka jest pusta");
        }
        A removedValue = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return removedValue;
    }
}