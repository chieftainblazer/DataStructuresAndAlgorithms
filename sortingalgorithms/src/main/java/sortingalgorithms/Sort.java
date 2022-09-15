package sortingalgorithms;

import java.util.Arrays;
import java.util.List;

/**
 * Class Sort is used to sort an array of elements is ascending order.
 *
 * @param <T> The data type of the element.
 */
public class Sort<T extends Comparable<? super T>> {
    private T[] elements;       // The elements to compare.

    /**
     * Constructs a Sort which can be used to sort arrays in ascending order.
     * @param elements
     */
    public Sort(T[] elements) {
        this.elements = elements;
    }

    /**
     * Uses the insertion sort algorithm to sort the array.
     */
    public void insertionSort() {
        for (int i = 1; i < elements.length; i++) {
            T temp = elements[i];
            for (int j = i; j > 0; j--) {
                if (temp.compareTo(elements[j - 1]) < 0) {
                    elements[j] = elements[j - 1];
                    elements[j - 1] = temp;
                }
            }
        }
    }

    /**
     * Uses the Selection Sort algorithm to sort the array.
     */
    public void selectionSort() {
        for (int i = 0; i < elements.length; i++) {
            T currentMinimum = elements[i];
            int indexToRemember = i;
            for (int j = i + 1; j < elements.length; j++) {
                T currentItem = elements[j];
                if (currentItem.compareTo(currentMinimum) < 0) {
                    currentMinimum = currentItem;
                    indexToRemember = j;
                }
            }
            T temp = elements[i];
            elements[i] = currentMinimum;
            elements[indexToRemember] = temp;
        }
    }

    /**
     * Uses HeapSort to sort the array.
     */
    public void heapSort() {
        MaxHeap<T> heap = new MaxHeap<>(elements);
        heap.buildHeap();
    }

    /**
     * Uses the Merge Sort Algorithm to sort the array.
     */

}
