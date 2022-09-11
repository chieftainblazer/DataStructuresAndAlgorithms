package deques;
public class LinkedDequeTests extends BaseDequeTests {
    @Override
    protected <T> Deque<T> createDeque() {
        return new LinkedDeque<>();
    }
}

