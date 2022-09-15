package sortingalgorithms;

/**
 * Custom Heap Class that can be used with the HeapSort Algorithm.
 *
 * @param <T> The data type of the elements.
 */
public class MaxHeap<T extends Comparable<? super T>> {
    private T[] heap;           // Contains all elements.
    private int size;           // Contains the size of the elements.

    /**
     * Constructs a MaxHeap with the given elements.
     *
     * @param elements the array that contains al the elements to be in the Heap.
     */
    public MaxHeap(T[] elements) {
        this.heap = elements;
        this.size = elements.length;
    }

    /**
     * Uses Floyd's Algorithm to convert an array to a valid Heap and proceeds to
     * sort the array.
     */
    public void buildHeap() {
        for (int i = size; i >= 0; i--) {
            percolateDown(i);
        }
        for (int i = size - 1; i > 0; i--) {
            heap[i] = removeMax();
        }
    }

    /**
     * Returns the maximum value in the heap.
     *
     * @return return the maximum value in the heap.
     */
    private T removeMax() {
        T itemToRemove = heap[0];
        T lastItemInHeap = heap[size - 1];
        size -= 1;
        heap[0] = lastItemInHeap;
        percolateDown(0);
        return itemToRemove;
    }

    /**
     * Recursively swaps elements in the heap to satisfy the Heap invariant of the highest
     * value always at the top.
     *
     * @param index the index of the current element.
     */
    private void percolateDown(int index) {
        int indexOfLeftChild = 2 * index + 1;
        int indexOfRightChild = 2 * index + 2;
        if (indexOfLeftChild >= size && indexOfRightChild >= size) {
            return;
        } else if (indexOfRightChild >= size) {
            indexOfRightChild = indexOfLeftChild;
        }
        int childWithHighestValue = getIndexOfChildWithHighestValue(indexOfLeftChild, indexOfRightChild);
        if (heap[index].compareTo(heap[childWithHighestValue]) > 0) {
            // Do nothing
        } else {
            swapElements(index, childWithHighestValue);
            percolateDown(childWithHighestValue);
        }
    }

    /**
     * Returns the index of the child with the highest value.
     *
     * @param indexOfLeftChild the index of the left child.
     * @param indexOfRightChild the index of the right child.
     * @return
     */
    private int getIndexOfChildWithHighestValue(int indexOfLeftChild, int indexOfRightChild) {
        if (heap[indexOfLeftChild].compareTo(heap[indexOfRightChild]) > 0) {
            return indexOfLeftChild;
        } else {
            return indexOfRightChild;
        }
    }

    /**
     * Swaps two elements at the two given positions.
     *
     * @param indexOfFirstItem the index of the first element.
     * @param indexOfSecondItem the index of the second element.
     */
    private void swapElements(int indexOfFirstItem, int indexOfSecondItem) {
        T parent = heap[indexOfFirstItem];
        T child = heap[indexOfSecondItem];
        heap[indexOfFirstItem] = child;
        heap[indexOfSecondItem] = parent;
    }
}
