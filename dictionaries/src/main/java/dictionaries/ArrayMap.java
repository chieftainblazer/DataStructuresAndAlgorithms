package dictionaries;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayMap<K extends Comparable<? super K>, V> extends AbstractIterableMap<K, V> {
    public static final int INITIAL_CAPACITY = 10;
    private SimpleEntry<K, V>[] entries;
    private int size;

    public ArrayMap() {
        this(INITIAL_CAPACITY);
    }

    public ArrayMap(int initialCapacity) {
        entries = new SimpleEntry[initialCapacity];
        size = 0;
    }

    @Override
    public V get(Object key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                return entries[i].getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size == entries.length) {
            resize(size * 2);
        }
        V oldValue = null;
        int trackOldSize = size;
        size += 1;      // We assume to always add a new value. If not, then we decrease it in loop.
        for (int i = 0; i <= trackOldSize; i++) {
            if (i < trackOldSize && Objects.equals(entries[i].getKey(), key)) {  // Update the Value if key is the same
                size -= 1;      // Our assumption is wrong. Decrease the size.
                oldValue = entries[i].getValue();
                entries[i].setValue(value);
                break;
            }
            if (i == trackOldSize) {    // The key is new, so we must be at the index equal to size. Insert it in there.
                entries[i] = new SimpleEntry<>(key, value);
            }
        }
        return oldValue;

    }

    @Override
    public V remove(Object key) {
        V oldValue = null;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                oldValue = entries[i].getValue();
                entries[i] = entries[size - 1];
                entries[size - 1] = null;
                size -= 1;
                break;
            }
        }
        return oldValue;
    }

    @Override
    public void clear() {
        entries = new SimpleEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.get(key) != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new ArrayMapIterator<>(entries);
    }

    private void resize(int newCapacity) {
        SimpleEntry<K, V>[] newEntries = new SimpleEntry[newCapacity];
        for (int i = 0; i < size; i++) {
            newEntries[i] = entries[i];
        }
        entries = newEntries;
    }


    private class ArrayMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private SimpleEntry<K, V>[] entries;
        private int index;

        public ArrayMapIterator(SimpleEntry<K, V>[] entries) {
            this.entries = entries;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index != ArrayMap.this.size();
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return entries[index++];
        }
    }
}
