package dictionaries;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class ChainedHashMap is a Hash Table that uses my implementation {@link ArrayMap} as the underlying
 * structure for when we are hashing at a spot.. It extends all the methods from {@link AbstractIterableMap} and
 * overrides them with the correct implementation in here.
 *
 * For more documentation, see {@link AbstractIterableMap}.
 * @param <K> The data type of the key.
 * @param <V> The data type of the value.
 */
public class ChainedHashMap<K extends Comparable<? super K>, V> extends AbstractIterableMap<K, V> {
    private static final double DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD = 1;
    private static final int DEFAULT_INITIAL_CHAIN_COUNT = 10;
    private static final int DEFAULT_INITIAL_CHAIN_CAPACITY = 10;

    private AbstractIterableMap<K, V>[] chains;             // Contains the map.
    private double loadingFactor;                           // This is used when we need to resize the array.
    private int arrayMapSize;                               // The size of the array we're hashing at.
    private int totalBuckets;                               // The total number of elements.

    /**
     * Constructs a ChainedHashMap with all default options.
     */
    public ChainedHashMap() {
        this(DEFAULT_RESIZING_LOAD_FACTOR_THRESHOLD, DEFAULT_INITIAL_CHAIN_COUNT, DEFAULT_INITIAL_CHAIN_CAPACITY);
    }

    /**
     * Constructs a ChainedHashMap with the given parameters.
     *
     * @param resizingLoadFactorThreshold the loading factor to resize.
     * @param initialChainCount the total number of spots available in our hash table.
     * @param chainInitialCapacity the number of elements in our underlying ArrayMap implementation.
     */
    public ChainedHashMap(double resizingLoadFactorThreshold, int initialChainCount, int chainInitialCapacity) {
        chains = new AbstractIterableMap[initialChainCount];
        arrayMapSize = chainInitialCapacity;
        loadingFactor = resizingLoadFactorThreshold;
        totalBuckets = 0;
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
        if (key == null) {
            return null;
        } else {
            int index = getIndex(key);
            if (chains[index] == null || !chains[index].containsKey(key)) {
                return null;
            } else {
                return chains[index].get(key);
            }
        }
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
        if ((double) totalBuckets / chains.length == loadingFactor) {
            resize();
        }
        int index = getIndex(key);
        if (chains[index] == null) {
            chains[index] = new ArrayMap<>(arrayMapSize);
        }
        if (!chains[index].containsKey(key)) {
            totalBuckets += 1;
        }
        V prevValue = chains[index].put(key, value);
        return prevValue;
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
        if (key == null) {
            return null;
        }
        int index = getIndex(key);
        if (chains[index] == null || !chains[index].containsKey(key)) {
            return null;
        }
        totalBuckets -= 1;
        return chains[index].remove(key);
    }

    /**
     * Removes all the mappings from this map.
     * The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        totalBuckets = 0;
        chains = new AbstractIterableMap[chains.length];
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    @Override
    public int size() {
        return totalBuckets;
    }

    /**
     * Returns an iterator that, when used, will yield all key-value
     * mappings contained within this map.
     */
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new ChainedHashMapIterator(this.chains);
    }

    /**
     * Resizes the hash table with doubled the capacity.
     */
    private void resize() {
        chains = Arrays.copyOf(chains, chains.length * 2);
    }

    /**
     * Returns the location of the given key when applying the hash function.
     *
     * @param key the given element.
     * @return the location of where this element is supposed to go in our hash table.
     */
    private int getIndex(Object key) {
        if (key == null) {      // If key is null then our index is always 0.
            return 0;
        }
        return Math.abs(key.hashCode()) % chains.length;
    }

    /**
     * Class ChainedHashMapIterator is used to model an iterator that can be used to traverse
     * the hash table.
     *
     * @param <K> the data type of the key.
     * @param <V> the data type of the value.
     */
    private static class ChainedHashMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private AbstractIterableMap<K, V>[] chains;
        private Iterator<Map.Entry<K, V>> iterator;
        private int trackIndex;

        public ChainedHashMapIterator(AbstractIterableMap<K, V>[] chains) {
            this.chains = chains;
            trackIndex = 0;
            iterator = null;
            arrayMap();
        }

        @Override
        public boolean hasNext() {
            if (iterator == null) {
                return false;
            }
            return iterator.hasNext();
        }

        @Override
        public Entry<K, V> next() {
            if (iterator == null && trackIndex == chains.length) {
                throw new NoSuchElementException();
            }
            if (!iterator.hasNext()) {
                trackIndex++;
                arrayMap();
            }
            return iterator.next();
        }

        /**
         * This method places the current iterator pointer at a non-null location in our hash table
         * that can then be used to traverse the elements in the ArrayMap at that location.
         */
        private void arrayMap() {
            boolean test = false;
            while (!test && trackIndex != chains.length) {
                if (chains[trackIndex] != null && chains[trackIndex].iterator().hasNext()) {
                    iterator = chains[trackIndex].iterator();
                    test = true;
                } else {
                    trackIndex++;
                }
            }
        }
    }
}
