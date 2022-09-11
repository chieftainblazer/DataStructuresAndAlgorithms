package deques;

public class ArrayDequeTests extends BaseDequeTests {
    @Override
    protected <T> Deque<T> createDeque() {
        return new ArrayDeque<>();
    }
}
