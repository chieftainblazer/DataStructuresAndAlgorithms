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
    public void mergeSort() {
        T[] tmpArray = (T[]) new Comparable[elements.length];
        int left = 0;
        int right = elements.length - 1;
        mergeSort(elements, tmpArray, left, right);
    }

    /**
     * Internal method that merges two sorted halves of a subarray.
     *
     * @param elements an array of Comparable items.
     * @param tmpArray an array to place the merge result.
     * @param left the left-most index of the subarray.
     * @param right the right most-index of the subarray.
     */
    private void mergeSort(T[] elements, T[] tmpArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(elements, tmpArray, left, center);
            mergeSort(elements, tmpArray, center + 1, right);
            merge(elements, tmpArray, left, center + 1, right);
        }
    }

    /**
     * Internal method that merges two sorted halves of a subarray.
     *
     * @param elements an array of Comparable items.
     * @param tmpArray an array to place the merge result.
     * @param leftPos the left-most index of the subarray.
     * @param rightPos the index of the start of the second half.
     * @param rightEnd the right-most index of the subarray.
     */
    private void merge(T[] elements, T[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElements = rightEnd - leftPos + 1;
        while (leftPos <= leftEnd && rightPos <= rightEnd) {
            if (elements[leftPos].compareTo(elements[rightPos]) <= 0) {
                tmpArray[tmpPos++] = elements[leftPos++];
            } else {
                tmpArray[tmpPos++] = elements[rightPos++];
            }
        }
        while (leftPos <= leftEnd) {
            tmpArray[tmpPos++] = elements[leftPos++];
        }
        while (rightPos <= rightEnd) {
            tmpArray[tmpPos++] = elements[rightPos++];
        }
        // Copies the tmpArray back.
        for (int i = 0; i < numElements; i++) {
            elements[rightEnd] = tmpArray[rightEnd];
            rightEnd--;
        }
    }
}
