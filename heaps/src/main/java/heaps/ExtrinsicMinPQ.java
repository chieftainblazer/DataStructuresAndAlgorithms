package heaps;

public interface ExtrinsicMinPQ<T> {

    /**
     * Adds an item with the given priority value.
     * @throws IllegalArgumentException if the given item is null or if the item
     * is already present in the PQ.
     */
    void add (T item, double priority);

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     */
    boolean contains(T item);

    /**
     * Returns the item with the least-valued priority.
     * @throws java.util.NoSuchElementException if the PQ is empty.
     */
    T peekMin();

    /**
     * Removes and returns the item with the least-valued priority.
     * @throws java.util.NoSuchElementException if the PQ is empty.
     */
    T removeMin();

    /**
     * Changes the priority of the given item.
     * @throws java.util.NoSuchElementException if the item is not present in the PQ.
     */
    void changePriority(T item, double priority);

    /**
     * Returns the number of items in the PQ.
     */
    int size();

    /**
     * Returns true if the PQ is empty, false otherwise.
     */
    boolean isEmpty();


    T get(int i);
}
