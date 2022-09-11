package heaps;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ArrayHeapMinPQTests {
    protected <T extends Comparable<T>> ExtrinsicMinPQ<T> createMinPQ() {
        return new ArrayHeapMinPQ<>();
    }

    @Nested
    @DisplayName("New Empty")
    class newEmpty {
        ExtrinsicMinPQ<Integer> setUpMinPQ() {
            return createMinPQ();
        }

        @Test
        void size_returns0() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            int output = pq.size();
            assertThat(output).isEqualTo(0);
        }

        @Test
        void contains_returnsFalse() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            boolean output = pq.contains(20);
            assertThat(output).isFalse();
        }

        @Test
        void peekMin_throwsNoSuchElement() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            assertThatThrownBy(() -> pq.peekMin()).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void changePriority_throwsNoSuchElement() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            assertThatThrownBy(() -> pq.changePriority(1, 7)).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        void add_nullItem_throwsIllegalArgument() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            assertThatThrownBy(() -> pq.add(null, 15)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Empty After Adding/Removing 3")
    class EmptyAfterAddRemove extends newEmpty {
        @Override
        ExtrinsicMinPQ<Integer> setUpMinPQ() {
            ExtrinsicMinPQ<Integer> pq = createMinPQ();
            pq.add(1, 1);
            pq.add(2, 2);
            pq.add(3, 3);
            pq.removeMin();
            pq.removeMin();
            pq.removeMin();
            return pq;
        }
    }

    @Nested
    @DisplayName("Add 3 Increasing Priority")
    class Add3Increasing {
        int min = 1;

        ExtrinsicMinPQ<Integer> setUpMinPQ() {
            ExtrinsicMinPQ<Integer> pq = createMinPQ();
            pq.add(1, 1);
            pq.add(2, 2);
            pq.add(3, 3);
            return pq;
        }

        @Test
        void size_returns3() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            int output = pq.size();
            assertThat(output).isEqualTo(3);
        }

        @Test
        void contains_withContainedItem_returnsTrue() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            boolean output = pq.contains(1);
            assertThat(output).isTrue();
        }

        @Test
        void contains_withNotContainedItem_returnsFalse() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            boolean output = pq.contains(412);
            assertThat(output).isFalse();
        }

        @Test
        void peekMin_returnsCorrectItem() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            int output = pq.peekMin();
            assertThat(output).isEqualTo(this.min);
        }

        @Test
        void removeMin_returnsCorrectItem() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            int output = pq.removeMin();
            assertThat(output).isEqualTo(this.min);
        }

        @Test
        void add_nullItem_throwsIllegalArgument() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            assertThatThrownBy(() -> pq.add(null, 15)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void add_duplicateItem_throwsIllegalArgument() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            assertThatThrownBy(() -> pq.add(1, 15)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("Add 3 Decreasing Priority")
    class Add3Decreasing extends Add3Increasing {
        Add3Decreasing() {
            min = 3;
        }

        @Override
        ExtrinsicMinPQ<Integer> setUpMinPQ() {
            ExtrinsicMinPQ<Integer> pq = createMinPQ();
            pq.add(1, 3);
            pq.add(2, 2);
            pq.add(3, 1);
            return pq;
        }
    }

    @Nested
    @DisplayName("Add 3 Arbitrary Priority")
    class Add3Arbitrary extends Add3Increasing {
        Add3Arbitrary() {
            min = 2;
        }

        @Override
        ExtrinsicMinPQ<Integer> setUpMinPQ() {
            ExtrinsicMinPQ<Integer> pq = createMinPQ();
            pq.add(1, 3);
            pq.add(2, 1);
            pq.add(3, 2);
            return pq;
        }
    }

    @Nested
    @DisplayName("Add 3 Same Priority")
    class Add3Same {
        ExtrinsicMinPQ<Integer> setUpMinPQ() {
            ExtrinsicMinPQ<Integer> pq = createMinPQ();
            pq.add(1, 7);
            pq.add(2, 7);
            pq.add(3, 7);
            return pq;
        }

        @Test
        void size_returns3() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            int output = pq.size();
            assertThat(output).isEqualTo(3);
        }

        @Test
        void contains_withContainedItem_returnsTrue() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            boolean output = pq.contains(1);
            assertThat(output).isTrue();
        }

        @Test
        void removeMinRepeatedly_returnsAllItems() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            Integer[] output = new Integer[3];
            for (int i = 0; i < pq.size(); i++) {
                output[i] = pq.get(i + 1);
            }
            assertThat(output).containsExactlyInAnyOrder(1, 2, 3);
        }
    }

    @Nested
    @DisplayName("Add 10 Increasing Priority")
    class Add10Increasing {
        Integer[] correctOrdering = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        ExtrinsicMinPQ<Integer> setUpMinPQ() {
            ExtrinsicMinPQ<Integer> pq = createMinPQ();
            pq.add(1, 1);
            pq.add(2, 2);
            pq.add(3, 3);
            pq.add(4, 4);
            pq.add(5, 5);
            pq.add(6, 6);
            pq.add(7, 7);
            pq.add(8, 8);
            pq.add(9, 9);
            pq.add(10, 10);
            return pq;
        }

        @Test
        void size_returns10() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            int output = pq.size();
            assertThat(output).isEqualTo(10);
        }

        @Test
        void removeMinRepeatedly_returnsItemsInCorrectOrder() {
            ExtrinsicMinPQ<Integer> pq = setUpMinPQ();
            Integer[] output = new Integer[10];
            for (int i = 0; i < pq.size(); i++) {
                output[i] = pq.get(i + 1);
            }
            assertThat(output).containsExactly(correctOrdering);
        }
    }

    @Nested
    @DisplayName("Add 10 Arbitrary Priority while changing priorities")
    class Add10Arbitrary extends Add10Increasing {
        Add10Arbitrary() {
            this.correctOrdering = new Integer[]{5, 8, 7, 1, 10, 6, 3, 2, 9, 4};
        }

        @Override
        ExtrinsicMinPQ<Integer> setUpMinPQ() {
            ExtrinsicMinPQ<Integer> pq = createMinPQ();
            pq.add(1, 3);
            pq.add(2, 9);
            pq.add(3, 7);
            pq.add(4, 4);
            pq.add(5, 1);
            pq.add(6, 8);
            pq.add(7, 5);
            pq.add(8, 2);
            pq.add(9, 6);
            pq.add(10, 10);
            pq.changePriority(10, 1);
            pq.changePriority(10, 10);
            pq.changePriority(4, 11);
            return pq;
        }
    }


}
