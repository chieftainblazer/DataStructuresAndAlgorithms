package sortingalgorithms;

import java.util.Arrays;

public class Sort<T extends Comparable<? super T>> {
    private T[] elements;

    public Sort(T[] elements) {
        this.elements = elements;
    }

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
}
