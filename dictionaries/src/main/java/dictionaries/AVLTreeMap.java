package dictionaries;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class AVLTreeMap is a Map that uses an AVL Tree as its
 * implementation. It extends all the methods from {@link AbstractIterableMap} and
 * overrides them with the correct implementation in here.
 *
 * It does not allow for null keys.
 *
 * For more documentation, see {@link AbstractIterableMap}.
 * @param <K> The data type of the key.
 * @param <V> The data type of the value.
 */

public class AVLTreeMap<K extends Comparable<? super K>, V> extends AbstractIterableMap<K, V> {
    private AVLNode<K, V> overallRoot;              // The node that roots the tree.
    private int size;                               // The size of the tree.

    /**
     * Constructs an empty AVLTreeMap.
     */
    public AVLTreeMap() {
        overallRoot = null;
        size = 0;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     * @throws ClassCastException   if the class of the given key is not the
     *                              same as the class of the current keys in this map.
     * @throws NullPointerException if the given key is {@code null}
     */
    @Override
    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (size > 0 && key.getClass() != overallRoot.key.getClass()) {
            throw new ClassCastException();
        }
        AVLNode<K, V> nodeToGet = getNode(overallRoot, (K) key);
        if (nodeToGet == null) {
            return null;
        }
        return nodeToGet.value;
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
    public V put(K key, V value) {
        AVLNode<K, V> oldNode = new AVLNode<>(null, null);
        overallRoot = put(overallRoot, oldNode, key, value);
        return oldNode.value;
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
     * @throws ClassCastException if the given key class doesn't match the class
     *         of the keys in the map.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with {@code key}, or
     *         {@code null} if there was no mapping for {@code key}.
     */
    @Override
    public V remove(Object key) {
        if (size == 0) {
            return null;
        }
        if (size > 0 && key.getClass() != overallRoot.key.getClass()) {
            throw new ClassCastException();
        }
        AVLNode<K, V> oldNode = new AVLNode<>(null, null);
        overallRoot = remove(overallRoot, oldNode, (K) key);
        return oldNode.value;
    }

    /**
     * Removes all the mappings from this map.
     * The map will be empty after this call returns.
     */
    @Override
    public void clear() {
        overallRoot = null;
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
     * @throws ClassCastException   if the given key class is not the same as the
     *                              class of the keys in the map.
     * @throws NullPointerException if the given key is {@code null}.
     */
    @Override
    public boolean containsKey(Object key) {
        if (key == null) {
            throw new NullPointerException();
        }
        if (size > 0 && key.getClass() != overallRoot.key.getClass()) {
            throw new ClassCastException();
        }
        return getNode(overallRoot, (K) key) != null;
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
        return new AVLTreeMapIterator<>();
    }

    /**
     * Helper method that gets the node in tree that has the given key. Returns
     * the node that has the same given key or {@code null} if there is no
     * mapping with this key.
     *
     * @param current The current node in the tree.
     * @param key     The key of the node that we are looking for.
     * @return the node with the mapping of the given key, or {@code null} if
     * this map contains no mapping for the key.
     */
    private AVLNode<K, V> getNode(AVLNode<K, V> current, K key) {
        if (current == null) {
            return null;
        }
        int compare = key.compareTo(current.key);
        if (compare == 0) {
            return current;
        } else if (compare < 0) {
            return getNode(current.left, key);
        } else {
            return getNode(current.right, key);
        }
    }

    /**
     * Helper method which builds the tree structure upon the insertion of a
     * node in the tree. It returns the newly created node or updates the node
     * that has an existing key with the given key and updates its given value.
     * The given oldNode is used to carry information of the value mapped by
     * the specified key, or {@code null} if the given key has no mapping.
     *
     * @param current  The current node in the tree.
     * @param oldNode  The node which holds information of the node to be updated.
     * @param key      The key to put in the tree.
     * @param newValue The value to put in the tree.
     * @return a node of where we are updating or making a new node.
     */
    private AVLNode<K, V> put(AVLNode<K, V> current, AVLNode<K, V> oldNode, K key, V newValue) {
        if (current == null) {
            size++;
            return new AVLNode<>(key, newValue);
        }
        int compare = key.compareTo(current.key);
        if (compare == 0) {
            oldNode.value = current.value;
            current.value = newValue;
        } else if (compare < 0) {
            current.left = put(current.left, oldNode, key, newValue);
        } else {
            current.right = put(current.right, oldNode, key, newValue);
        }
        updateHeight(current);
        return balanceTree(current);
    }

    /**
     * Helper method which balances the tree to follow the AVL invariant of a
     * valid BST and the height of the left subtree and right subtree can differ
     * at most 1. Returns the node that is part of the subset of the {@code overallRoot}
     * which roots a tree that is balanced.
     *
     * @param current The current node.
     * @return returns the node that roots a balanced tree.
     */
    private AVLNode<K, V> balanceTree(AVLNode<K, V> current) {
        if (current == null) {
            return null;
        }
        int differenceInHeight = getHeight(current.left) - getHeight(current.right);
        if (differenceInHeight > 1) {             // Left side is heavier. Perform Right Rotation.
            if (getHeight(current.left.right) > getHeight(current.left.left)) {       // Left-Right Rotation case
                current.left = leftRotation(current.left);
            }
            current = rightRotation(current);

        } else if (differenceInHeight < -1) {     // Right side is heavier. Perform Left Rotation.
            if (getHeight(current.right.left) > getHeight(current.right.right)) {       // Right-Left Rotation case
                current.right = rightRotation(current.right);
            }
            current = leftRotation(current);
        }
        return current;
    }

    /**
     * Helper method which performs a right rotation. Returns the node that
     * roots the tree after performing a right rotation.
     *
     * @param node The node that roots the tree to be applied right rotation.
     * @return the node that roots the tree.
     */
    private AVLNode<K, V> rightRotation(AVLNode<K, V> node) {
        AVLNode<K, V> subtreeToSave = node.left.right;
        node.left.right = node;
        node = node.left;
        node.right.left = subtreeToSave;
        updateHeight(node.right);
        updateHeight(node);
        return node;
    }

    /**
     * Helper method which performs a left rotation. Returns the node that
     * roots the tree after performing a left rotation.
     *
     * @param node The node that roots the tree to be applied right rotation.
     * @return
     */
    private AVLNode<K, V> leftRotation(AVLNode<K, V> node) {
        AVLNode<K, V> subtreeToSave = node.right.left;
        node.right.left = node;
        node = node.right;
        node.left.right = subtreeToSave;
        updateHeight(node.left);
        updateHeight(node);
        return node;
    }

    /**
     * Updates the height of the given node.
     *
     * @param node The node that will get its height updated.
     */
    private void updateHeight(AVLNode<K, V> node) {
        if (node != null) {
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        }
    }

    /**
     * Returns the height of a node. If the node is {@code null} then it returns -1,
     * otherwise it returns the height.
     *
     * @param node the node to be examined.
     * @return the height of this node.
     */
    private int getHeight(AVLNode<K, V> node) {
        if (node == null) {
            return -1;
        } else {
            return node.height;
        }
    }

    /**
     * Helper method which removes the node that has the given key. If the node
     * to be removed has no children, so is a leaf, then we just remove it. If
     * the node has exactly one child only, then we remove it and the child is
     * the node to take place instead. If the node to be removed has exactly two
     * children then we seek the node with the highest {@code k} in the left
     * subtree and replace it with it. Returns {@code null} if the given key has no
     * mapping in this tree, otherwise it returns the node that was removed.
     *
     * @param current The current node in the tree.
     * @param oldNode The node that will hold the information of the node that
     *                was removed.
     * @param key     The key of the node we are looking to remove.
     * @return the node that was removed, or {@code null} if the tree has no
     * mapping of the given key.
     */
    private AVLNode<K, V> remove(AVLNode<K, V> current, AVLNode<K, V> oldNode, K key) {
        if (current == null) {
            return null;
        }
        int compare = key.compareTo(current.key);
        if (compare == 0) {
            size--;
            oldNode.key = current.key;
            oldNode.value = current.value;
            if (current.left == null && current.right == null) {     // Easy case. Node to be deleted has no children.
                return null;
            } else if (current.left == null && current.right != null) { // Easy case. Node only has one child.
                return current.right;
            } else if (current.left != null && current.right == null) { // Easy case. Node only has one child.
                return current.left;
            } else {                // Hard case. We have to grab the largest key in the left subtree to swap.
                AVLNode<K, V> largestNode = new AVLNode<>(null, null);
                current.left = findBiggestInLeftSubtree(current.left, largestNode);
                current.key = largestNode.key;
                current.value = largestNode.value;
                updateHeight(current.left);
                updateHeight(current.right);
                current.left = balanceTree(current.left);
                current.right = balanceTree(current.right);
                current = balanceTree(current);
                return current;
            }
        } else if (compare < 0) {
            current.left = remove(current.left, oldNode, key);
            updateHeight(current);
            current = balanceTree(current);

        } else {
            current.right = remove(current.right, oldNode, key);
            updateHeight(current);
            current = balanceTree(current);
        }
        return current;
    }

    /**
     * Helper method which is used to return the updated left subtree with
     * the highest node removed.
     *
     * @param current     The current node in the tree.
     * @param largestNode The node that will hold the data of the highest node
     *                    in the left-subtree.
     * @return the updated left-subtree with the highest node removed.
     */
    private AVLNode<K, V> findBiggestInLeftSubtree(AVLNode<K, V> current, AVLNode<K, V> largestNode) {
        if (current.right == null) {
            largestNode.key = current.key;
            largestNode.value = current.value;
            return current.left;
        } else {
            current.right = findBiggestInLeftSubtree(current.right, largestNode);
            return current;
        }
    }

    /**
     * Class AVLTreeMapIterator is used to represent the iterator that
     * traverses the map contents.
     *
     * @param <K> The data type of the key.
     * @param <V> The data type of the value.
     */
    private class AVLTreeMapIterator<K, V> implements Iterator<Map.Entry<K, V>> {
        private SimpleEntry<K, V>[] entries;
        private int current;

        public AVLTreeMapIterator() {
            entries = new SimpleEntry[AVLTreeMap.this.size()];
            current = 0;
            inorder((AVLTreeMap.AVLNode<K, V>) AVLTreeMap.this.overallRoot);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < entries.length;
        }

        @Override
        public Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return entries[current++];
        }

        private void inorder(AVLTreeMap.AVLNode<K, V> currentNode) {
            if (currentNode == null) {
                // Do nothing
            } else {
                inorder(currentNode.left);
                entries[current++] = new SimpleEntry<>(currentNode.key, currentNode.value);
                inorder(currentNode.right);
            }
        }
    }

    /**
     * Represents a node suitable for an AVL tree.
     *
     * @param <K> the data type of the key.
     * @param <V> the data type of the value.
     */
    public static class AVLNode<K, V> {
        AVLNode<K, V> left;
        AVLNode<K, V> right;
        int height;
        K key;
        V value;

        AVLNode(K key, V value) {
            this.height = 0;
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
}
