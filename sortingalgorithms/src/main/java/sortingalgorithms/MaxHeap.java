package sortingalgorithms;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap<T extends Comparable<? super T>> {
    private List<T> heap;

    public MaxHeap(T[] elements) {
        this.heap = new ArrayList<>();
        for (int i = 0; i < elements.length; i++) {
            heap.add(i, elements[i]);
        }
    }

    public List<T> buildHeap() {
        for (int i = heap.size(); i >= 0; i--) {
            percolateDown(i);
        }
        List<T> sortedHeap = new ArrayList<>();
        while (heap.size() > 1) {
            sortedHeap.add(removeMin());
        }
        sortedHeap.add(heap.get(0));
        return sortedHeap;
    }

    private T removeMin() {
        T itemToRemove = heap.get(0);
        T lastItemInHeap = heap.get(heap.size() - 1);
        heap.remove(heap.size() - 1);
        heap.set(0, lastItemInHeap);
        percolateDown(0);
        return itemToRemove;
    }

    private void percolateDown(int index) {
        int indexOfLeftChild = 2 * index + 1;
        int indexOfRightChild = 2 * index + 2;
        if (indexOfLeftChild >= heap.size() && indexOfRightChild >= heap.size()) {
            return;
        } else if (indexOfRightChild >= heap.size()) {
            indexOfRightChild = indexOfLeftChild;
        }
        int childWithHighestValue = getIndexOfChildWithLowestPriority(indexOfLeftChild, indexOfRightChild);
        if (heap.get(index).compareTo(heap.get(childWithHighestValue)) < 0) {
            // Do nothing
        } else {
            swapElements(index, childWithHighestValue);
            percolateDown(childWithHighestValue);
        }
    }

    private int getIndexOfChildWithLowestPriority(int indexOfLeftChild, int indexOfRightChild) {
        if (heap.get(indexOfLeftChild).compareTo(heap.get(indexOfRightChild)) < 0) {
            return indexOfLeftChild;
        } else {
            return indexOfRightChild;
        }
    }

    private void swapElements(int indexOfFirstItem, int indexOfSecondItem) {
        T parent = heap.get(indexOfFirstItem);
        T child = heap.get(indexOfSecondItem);
        heap.set(indexOfFirstItem, child);
        heap.set(indexOfSecondItem, parent);
    }
}
