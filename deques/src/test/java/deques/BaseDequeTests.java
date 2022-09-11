package deques;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseDequeTests {
    protected abstract <T> Deque<T> createDeque();

    @Test
    void size_wheEmpty_is0() {
        Deque<String> deque = createDeque();
        assertThat(deque).isEmpty();
    }

    @Test
    void addFirst() {
        Deque<Integer> deque = createDeque();
        deque.addFirst(56);
        assertThat(deque.get(0)).isEqualTo(56);
    }

    @Test
    void get_at0_whenEmpty_returnsNull() {
        Deque<String> deque = createDeque();
        String output = deque.get(0);
        assertThat(output).isNull();
    }

    @Test
    void get_atNegative_whenEmpty_returnsNull() {
        Deque<String> deque = createDeque();
        String output = deque.get(-1);
        assertThat(output).isNull();
    }

    @Test
    void get_atPositive_whenEmpty_returnsNull() {
        Deque<String> deque = createDeque();
        String output = deque.get(0);
        assertThat(output).isNull();
    }

    @Test
    void usingMultipleDequesSimultaneously_doesNotCauseInterference() {
        Deque<Integer> d1 = createDeque();
        Deque<Integer> d2 = createDeque();
        d1.addFirst(1);
        d2.addFirst(2);
        d1.addFirst(3);

        assertThat(d1).hasSize(2);
        assertThat(d2).hasSize(1);
    }

    @Test
    void size_with1Item_is1() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        assertThat(deque).hasSize(1);
    }

    @Test
    void get_at0__with1Item_returnsItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        String output = deque.get(0);
        assertThat(output).isEqualTo("s");
    }

    @Test
    void remove_afterAdd1Item_toSameSide_returnsItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        String output = deque.removeFirst();
        assertThat(output).isEqualTo("s");
    }

    @Test
    void remove_afterAdd1Item_toOppositeSide_returnsItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("s");
        String output = deque.removeLast();
        assertThat(output).isEqualTo("s");
    }

    @Test
    void remove_whenEmpty_returnsNull() {
        Deque<String> deque = createDeque();
        String output = deque.removeFirst();
        assertThat(output).isNull();
    }

    @Test
    void getEach_afterAddToOppositeEnds_returnsCorrectItems() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        // this assertion calls `get` for each index
        assertThat(deque).containsExactly("a", "b");
    }

    @Test
    void size_afterAddToOppositeEnds_is2() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        assertThat(deque).hasSize(2);

    }

    @Test
    void remove_afterAddToOppositeEnds_returnsCorrectItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        String output = deque.removeFirst();
        assertThat(output).isEqualTo("a");
    }

    @Test
    void size_afterRemove_afterAddToOppositeEnds_is1() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        deque.removeFirst();
        assertThat(deque).hasSize(1);
    }

    @Test
    void remove_afterRemove_afterAddToOppositeEnds_returnsCorrectItem() {
        Deque<String> deque = createDeque();
        deque.addFirst("a");
        deque.addLast("b");
        deque.removeFirst();
        String output = deque.removeLast();
        assertThat(output).isEqualTo("b");
    }

    @Test
    void getEach_afterAddManyToSameSide_returnsCorrectItems() {
        Deque<Integer> deque = createDeque();
        IntStream.range(0, 20).forEach(deque::addLast);
        assertThat(deque).containsExactly(IntStream.range(0, 20).boxed().toArray(Integer[]::new));
    }

    @Test
    void remove_afterAddString_returnsCorrectString() {
        Deque<String> deque = createDeque();
        deque.addFirst("string");
        String s = deque.removeFirst();
        assertThat(s).isEqualTo("string");
    }

    @Test
    void remove_afterAddDouble_returnsCorrectDouble() {
        Deque<Double> deque = createDeque();
        deque.addFirst(3.1415);
        double d = deque.removeFirst();
        assertThat(d).isEqualTo(3.1415);
    }


    @Test
    void remove_afterAddBoolean_returnsCorrectBoolean() {
        Deque<Boolean> deque = createDeque();
        deque.addFirst(true);
        boolean b = deque.removeFirst();
        assertThat(b).isTrue();
    }

    @Test
    void trickyTest() {
        Deque<Integer> deque = createDeque();
        deque.addFirst(0);
        assertThat(deque.get(0)).isEqualTo(0);

        deque.addLast(1);
        assertThat(deque.get(1)).isEqualTo(1);

        deque.addFirst(-1);
        deque.addLast(2);
        assertThat(deque.get(3)).isEqualTo(2);

        deque.addLast(3);
        deque.addLast(4);

        assertThat(deque.removeFirst()).isEqualTo(-1);
        deque.addFirst(-1);
        assertThat(deque.get(0)).isEqualTo(-1);

        deque.addLast(5);
        deque.addFirst(-2);
        deque.addFirst(-3);

        assertThat(deque.removeFirst()).isEqualTo(-3);
        assertThat(deque.removeLast()).isEqualTo(5);
        assertThat(deque.removeLast()).isEqualTo(4);
        assertThat(deque.removeLast()).isEqualTo(3);
        assertThat(deque.removeLast()).isEqualTo(2);

        int actual = deque.removeLast();
        assertThat(actual).isEqualTo(1);
    }
}
