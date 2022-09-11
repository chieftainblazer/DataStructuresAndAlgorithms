package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private Node<T> front;
    private Node<T> back;
    private int size;

    public LinkedDeque() {
        front = new Node<>(null);
        back = new Node<>(null, front, null);
        front.next = back;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node<T> nodeToInsert = new Node<>(item, front, front.next);
        front.next = nodeToInsert;
        front.next.next.prev = nodeToInsert;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node<T> nodeToInsert = new Node<>(item, back.prev, back);
        back.prev = nodeToInsert;
        back.prev.prev.next = nodeToInsert;
        size++;
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        Node<T> firstItem = front.next;
        front.next = front.next.next;
        front.next.prev = front;
        size--;
        return firstItem.value;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        Node<T> lastItem = back.prev;
        back.prev = back.prev.prev;
        back.prev.next = back;
        size--;
        return lastItem.value;
    }

    @Override
    public T get(int index) {
        if (index < 0 ||index >= size) {
            return null;
        }
        Node<T> current;
        if (index < size / 2) {
            current = front.next;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.value;
        } else {
            current = back.prev;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current.value;
        }
    }

    @Override
    public int size() {
        return size;
    }
}
