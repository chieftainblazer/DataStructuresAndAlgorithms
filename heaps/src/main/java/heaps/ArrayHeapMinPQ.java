package heaps;

import java.util.*;

/**
 * Class ArrayHeapMinPQ represents a minimum priority queue.
 * @see ExtrinsicMinPQ for more documentation.
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    public static int INDEX_OF_MINIMUM_PRIORITY_ELEMENT = 1;
    private List<PriorityNode<T>> heap;
    private Map<T, Integer> mapOfIndices;
    private int size;

    /**
     * Constructs an ArrayHeapMinPQ which is a minimum priority queue.
     */
    public ArrayHeapMinPQ() {
        heap = new ArrayList<>();
        mapOfIndices = new HashMap<>();
        size = 0;
        heap.add(null);
    }

    @Override
    public void add(T item, double priority) {
        if (item == null || mapOfIndices.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        heap.add(new PriorityNode<>(item, priority));
        mapOfIndices.put(item, size + 1);
        percolateUp(size + 1);
        size++;
    }

    @Override
    public boolean contains(T item) {
        return mapOfIndices.containsKey(item);
    }

    @Override
    public T peekMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return heap.get(INDEX_OF_MINIMUM_PRIORITY_ELEMENT).getItem();
    }

    @Override
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        T itemToRemove = heap.get(INDEX_OF_MINIMUM_PRIORITY_ELEMENT).getItem();
        mapOfIndices.remove(heap.get(INDEX_OF_MINIMUM_PRIORITY_ELEMENT).getItem());
        PriorityNode<T> lastItemInHeap = heap.get(size);
        heap.set(INDEX_OF_MINIMUM_PRIORITY_ELEMENT, lastItemInHeap);
        heap.remove(size--);
        mapOfIndices.put(lastItemInHeap.getItem(), INDEX_OF_MINIMUM_PRIORITY_ELEMENT);
        percolateDown(INDEX_OF_MINIMUM_PRIORITY_ELEMENT);
        return itemToRemove;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!mapOfIndices.containsKey(item)) {
            throw new NoSuchElementException();
        }
        int indexOfItem = mapOfIndices.get(item);
        heap.get(indexOfItem).setPriority(priority);
        percolateUp(indexOfItem);
        indexOfItem = mapOfIndices.get(item);
        percolateDown(indexOfItem);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size != 0;
    }

    /**
     * Returns the element at the given index. Used for testing purposes.
     */
    public T get(int index) {
        return heap.get(index).getItem();
    }

    /**
     * Utility method that percolates up in the heap to fix the invariant where
     * each node is less than its parents. The given index is the position of the
     * node to percolate up.
     */
    private void percolateUp(int index) {
        if (index == 1 || heap.get(index).getPriority() >= heap.get(index / 2).getPriority()) {
            // Do nothing
        } else { // heap.get(index).getPriority() < heap.get(index / 2).getPriority()
            int indexOfParent = index / 2;
            int indexOfChild = index;
            swapElements(indexOfParent, indexOfChild);
            percolateUp(indexOfParent);
        }
    }

    /**
     * Utility method that percolates down in the heap to fix the invariant where
     * each node priority is less than or equal the priority of its children. The
     * given index is the position of the node to start percolating down.
     */
    private void percolateDown(int index) {
        int indexOfLeftChild = 2 * index;
        int indexOfRightChild = 2 * index + 1;
        if (indexOfLeftChild > size && indexOfRightChild > size) {
            return;
        } else if (indexOfRightChild > size) {
            indexOfRightChild = indexOfLeftChild;
        }
        int childWithLowestPriority = getIndexOfChildWithLowestPriority(indexOfLeftChild, indexOfRightChild);
        if (heap.get(index).getPriority() <= heap.get(childWithLowestPriority).getPriority()) {
            // Do nothing
        } else {
            swapElements(index, childWithLowestPriority);
            percolateDown(childWithLowestPriority);
        }
    }

    /**
     * Utility method that given the index of the left child and the index of the
     * right child, returns the index of which child has the smallest priority.
     */
    private int getIndexOfChildWithLowestPriority(int indexOfLeftChild, int indexOfRightChild) {
        if (heap.get(indexOfLeftChild).getPriority() <= heap.get(indexOfRightChild).getPriority()) {
            return indexOfLeftChild;
        } else {
            return indexOfRightChild;
        }
    }

    /**
     * Utility method that swaps the positions of the elements with the given
     * indexOfFirstItem and indexOfSecondItem.
     */
    private void swapElements(int indexOfFirstItem, int indexOfSecondItem) {
        PriorityNode<T> parent = heap.get(indexOfFirstItem);
        PriorityNode<T> child = heap.get(indexOfSecondItem);
        heap.set(indexOfFirstItem, child);
        heap.set(indexOfSecondItem, parent);
        mapOfIndices.put(parent.getItem(), indexOfSecondItem);
        mapOfIndices.put(child.getItem(), indexOfFirstItem);
    }
}
