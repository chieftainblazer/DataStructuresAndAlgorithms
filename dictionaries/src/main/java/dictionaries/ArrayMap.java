package dictionaries;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class ArrayMap is a Map that uses an array as its
 * implementation. It extends all the methods from {@link AbstractIterableMap} and
 * overrides them with the correct implementation in here.
 *
 * For more documentation, see {@link AbstractIterableMap}.
 * @param <K> The data type of the key.
 * @param <V> The data type of the value.
 */

public class ArrayMap<K extends Comparable<? super K>, V> extends AbstractIterableMap<K, V> {
    public static final int INITIAL_CAPACITY = 10;
    private SimpleEntry<K, V>[] entries;        // Contains all keys and values.
    private int size;                           // The size of the map.

    /**
     * Constructs an ArrayMap with a default capacity.
     */
    public ArrayMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs an ArrayMap that allows the client to specify the size of the Map.
     * @param initialCapacity the given size for the map.
     */
    public ArrayMap(int initialCapacity) {
        entries = new SimpleEntry[initialCapacity];
        size = 0;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     */
    @Override
    public V get(Object key) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                return entries[i].getValue();
            }
        }
        return null;
    }

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * {@code m} is said to contain a mapping for a key {@code k} if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * {@code true}.)
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     *         (A {@code null} return can also indicate that the map
     *         previously associated {@code null} with {@code key},
     *         if the implementation supports {@code null} values.)
     */
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

    /**
     * Removes the mapping for a key from this map if it is present
     * (optional operation). More formally, if this map contains a mapping
     * from key {@code k} to value {@code v} such that
     * {@code Objects.equals(key, k)}, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     *
     * Returns the value to which this map previously associated the key,
     * or {@code null} if the map contained no mapping for the key.
     *
     * A return value of {@code null} does not necessarily indicate that the map
     * contained no mapping for the key; it's also possible that the map
     * explicitly mapped the key to {@code null}.
     *
     * The map will not contain a mapping for the specified key once the
     * call returns.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     */
    @Override
    public V remove(Object key) {
        V oldValue = null;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(entries[i].getKey(), key)) {
                oldValue = entries[i].getValue();
                entries[i] = entries[size - 1];     // Swaps the entry for the last entry in the map for efficiency.
                entries[size - 1] = null;
                size -= 1;
                break;
            }
        }
        return oldValue;
    }

    /**
     * Removes all the mappings from this map.
     * The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        entries = new SimpleEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified
     * key.  More formally, returns {@code true} if and only if
     * this map contains a mapping for a key {@code k} such that
     * {@code Objects.equals(key, k)}.  (There can be
     * at most one such mapping.)
     *
     * @param key key whose presence in this map is to be tested
     * @return {@code true} if this map contains a mapping for the specified
     * key
     */
    @Override
    public boolean containsKey(Object key) {
        return this.get(key) != null;
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an iterator that, when used, will yield all key-value
     * mappings contained within this map.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new ArrayMapIterator<>(entries);
    }

    /**
     * Resizes the array to the given newCapcity. All entries will remain in the same
     * relative position as originially in the array.
     *
     * @param newCapacity The new capacity for the array.
     */
    private void resize(int newCapacity) {
        SimpleEntry<K, V>[] newEntries = new SimpleEntry[newCapacity];
        for (int i = 0; i < size; i++) {
            newEntries[i] = entries[i];
        }
        entries = newEntries;
    }


    /**
     * Class ArrayMapIterator is used to create an Iterator capable of traversing
     * through the map.
     *
     * @param <K> The data type of the key.
     * @param <V> The data type of the value.
     */
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
