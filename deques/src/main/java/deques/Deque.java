package deques;

import java.util.Queue;

/**
 * A custom Deque interface. Java already has its own Deque interface, but it has
 * so many methods that are annoying to implement.
 * @see java.util.Deque
 */
public interface Deque<T> extends Queue<T> {
    /**
     * Adds an item of type T to the front of the deque.
     */
    void addFirst(T item);


    /**
     * Adds an item of type T to the back of the deque.
     */
    void addLast(T item);

    /**
     * Removes and returns the item at the front of the deque.
     * Returns null if the size of deque is zero.
     */
    T removeFirst();

    /**
     * Removes and returns the item at the back of the deque.
     * Returns null if the size of the deque is zero.
     */
    T removeLast();

    /**
     * Returns the number of items in the deque.
     */
    int size();

    /**
     * Returns true if the deque is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next
     * item, and so forth. This method is there for testing purposes.
     */
    T get(int index);
}
