package deques;

import java.util.AbstractQueue;
import java.util.Iterator;

/**
 * AbstractDeque is used to represent the basic functionality of a Queue.
 * For more documentation:
 * @see AbstractQueue
 */
public abstract class AbstractDeque<T> extends AbstractQueue<T> implements Deque<T> {

    @Override
    public boolean offer(T t) {
        addLast(t);
        return true;
    }

    @Override
    public T poll() {
        return removeFirst();
    }

    @Override
    public T peek() {
        return get(0);
    }

    /**
     * A basic iterator that uses the get method. Iterates through the contents of the Queue.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < AbstractDeque.this.size();
            }

            @Override
            public T next() {
                return AbstractDeque.this.get(i++);
            }
        };
    }
}