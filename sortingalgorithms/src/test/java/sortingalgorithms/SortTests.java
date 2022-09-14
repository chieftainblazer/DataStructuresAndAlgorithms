package sortingalgorithms;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SortTests {

    /**----------------------------------- Insertion Sort Tests -------------------------------**/
    @Test
    void assert_InsertionSort_OneElement_works() {
        Integer[] array = new Integer[] {1};
        Sort sort = new Sort(array);
        sort.insertionSort();
        assertThat(array).isSorted();
    }

    @Test
    void assert_InsertionSort_TwoElements_works() {
        Integer[] array = new Integer[] {2, 1};
        Sort sort = new Sort(array);
        sort.insertionSort();
        assertThat(array).isSorted();
    }

    @Test
    void assert_InsertionSort_ThreeElements_works() {
        Integer[] array = new Integer[] {3, 2, 1};
        Sort sort = new Sort(array);
        sort.insertionSort();
        assertThat(array).isSorted();
    }

    @Test
    void assert_InsertionSort_20RandomElements_Orders_Correctly() {
        Integer[] array = new Integer[20];
        for (int i = 0; i < 20; i++) {
            array[i] = Integer.valueOf((int) (Math.random() * 20 + 1));
        }
        Sort sort = new Sort(array);
        sort.insertionSort();
        assertThat(array).isSorted();
    }

    /**----------------------------------- Selection Sort Tests -------------------------------**/
    @Test
    void assert_SelectionSort_OneElement_works() {
        Integer[] array = new Integer[] {1};
        Sort sort = new Sort(array);
        sort.selectionSort();
        assertThat(array).isSorted();
    }

    @Test
    void assert_SelectionSort_TwoElements_works() {
        Integer[] array = new Integer[] {2, 1};
        Sort sort = new Sort(array);
        sort.selectionSort();
        assertThat(array).isSorted();
    }

    @Test
    void assert_SelectionSort_ThreeElements_works() {
        Integer[] array = new Integer[] {3, 2, 1};
        Sort sort = new Sort(array);
        sort.selectionSort();
        assertThat(array).isSorted();
    }

    @Test
    void assert_SelectionSort_20RandomElements_Orders_Correctly() {
        Integer[] array = new Integer[20];
        for (int i = 0; i < 20; i++) {
            array[i] = Integer.valueOf((int) (Math.random() * 20 + 1));
        }
        Sort sort = new Sort(array);
        sort.selectionSort();
        assertThat(array).isSorted();
    }
}
