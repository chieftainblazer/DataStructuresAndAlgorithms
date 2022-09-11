package deques;

/**
 * Class ArrayDeque is used to build and represent a Deque.
 * @see Deque For more documentation.
 */
public class ArrayDeque<T> extends AbstractDeque<T> {
    public static final int INITIAL_CAPACITY = 10;      // Initial Capacity of deque.
    private T[] items;                                  // Represents the deque.
    private int size;                                   // Keep track of deque size.
    private int front;                                  // Pointer to front of deque.
    private int back;                                   // Pointer to back of deque.

    /**
     * Constructs an empty ArrayDeque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        front = 0;
        back = 1;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[front] = item;
        front = decrement(front, items.length);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[back] = item;
        back = increment(back, items.length);
        size++;
    }

    private void resize(int capacity) {
        T[] copyItems = (T[]) new Object[capacity];
        int trackFront = increment(front, size) % items.length;
        for (int index = 0; index < size; index++) {
            copyItems[index] = items[trackFront];
            trackFront = increment(trackFront, size);
        }
        front = copyItems.length - 1;
        back = items.length;
        items = copyItems;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        front = increment(front, items.length);
        T result = items[front];
        items[front] = null;
        size--;
        if (needsDownsize()) {
            resize(items.length / 2);
        }
        return result;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        back = decrement(back, items.length);
        T result = items[back];
        items[back] = null;
        size--;
        if (needsDownsize()) {
            resize(items.length / 2);
        }
        return result;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            int place = front + 1 + index;
            return items[place % items.length];
        }
    }

    @Override
    public int size() {
        return size;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        if (size >= 0) {
            int counter = 0;
            int i = increment(front, items.length);
            while (counter < size) {
                output.append(items[i]).append(" ");
                counter++;
            }
        }
        return output.toString();
    }

    /**
     * Returns true if the deque needs to be downsized. False otherwise.
     */
    private boolean needsDownsize() {
        return ((double) size) / items.length < 0.25 && items.length >= 16;
    }

    /**
     * Increments the pointer, either the front or back pointer which is given
     * by the given position. The given length is for the length of the deque.
     * Returns the value of the given position + 1.
     */
    private int increment(int position, int length) {
        if (position == length - 1) {
            return 0;
        } else {
            return position + 1;
        }
    }

    /**
     * Decrements the pointer, either the front or back pointer which is given
     * by the given position. The given length is for the length of the deque.
     * Returns the value of the given position - 1.
     */
    private int decrement(int position, int length) {
        if (position == 0) {
            return length - 1;
        } else {
            return position - 1;
        }
    }
}