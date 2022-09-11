package dictionaries;
import org.junit.jupiter.api.Test;


import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.data.MapEntry.entry;

public abstract class BaseTreeTests {
    public static final String KEY = "someKey";
    public static final String VAL = "someVal";
    public static final String OLD_VAL = "oldVal";

    protected abstract <K extends Comparable<? super K>, V> Map<K, V> createTreeMap();

    private void exhaust(Iterator<?> iterator) {
        while (iterator.hasNext()) {
            iterator.next();
        }
    }

    /** ----------------------------------- EMPTY MAP TESTS ---------------------------------------**/

    @Test
    void size_is0() {
        Map<String, String> map = createTreeMap();
        assertThat(map).hasSize(0);
    }

    @Test
    void containsKey_isFalse() {
        Map<String, String> map = createTreeMap();
        assertThat(map).doesNotContainKey(KEY);
    }

    @Test
    void get_returnsNull() {
        Map<String, String> map = createTreeMap();
        String output = map.get(KEY);
        assertThat(output).isNull();
    }

    @Test
    void put_returnsNull() {
        Map<String, String> map = createTreeMap();
        String output = map.put(KEY, VAL);
        assertThat(output).isNull();
    }

    @Test
    void remove_returnsNull() {
        Map<String, String> map = createTreeMap();
        String output = map.remove(KEY);
        assertThat(output).isNull();
    }

    @Test
    void iterator_hasNext_returnsFalse() {
        Map<String, String> map = createTreeMap();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        assertThat(iterator).isExhausted();
    }

    @Test
    void iterator_hasNext_twice_returnsFalse() {
        Map<String, String> map = createTreeMap();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        iterator.hasNext();
        assertThat(iterator).isExhausted();
    }

    @Test
    void iterator_next_throwsNoSuchElement() {
        Map<String, String> map = createTreeMap();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);
    }

    /** ----------------------------------- END OF EMPTY MAP TESTS ------------------------------------**/



    /** ----------------------------------- MAP WITH ONE ENTRY TESTS ----------------------------------**/

    Map<String, String> createMapWith1Entry() {
        Map<String, String> map = createTreeMap();
        map.put(KEY, VAL);
        return map;
    }

    @Test
    void size_is1() {
        Map<String, String> map = createMapWith1Entry();
        assertThat(map).hasSize(1);
    }

    @Test
    void containsKey_returnsTrue() {
        Map<String, String> map = createMapWith1Entry();
        assertThat(map).containsKey(KEY);
    }

    @Test
    void containsKey_withNewKey_returnsFalse() {
        Map<String, String> map = createMapWith1Entry();
        assertThat(map).doesNotContainKey("someOtherKey");
    }

    @Test
    void get_returnsCorrectValue() {
        Map<String, String> map = createMapWith1Entry();
        String output = map.get(KEY);
        assertThat(output).isEqualTo(VAL);
    }

    @Test
    void get_newKey_returnsNull() {
        Map<String, String> map = createMapWith1Entry();
        String output = map.get("someOtherKey");
        assertThat(output).isNull();
    }

    @Test
    void put_newKey_returnsNull() {
        Map<String, String> map = createMapWith1Entry();
        String output = map.put("someOtherKey", VAL);
        assertThat(output).isNull();
    }

    @Test
    void put_sameKey_returnsCorrectValue() {
        Map<String, String> map = createMapWith1Entry();
        String output = map.put(KEY, "someOtherVal");
        assertThat(output).isEqualTo(VAL);
    }

    @Test
    void put_sameKeyAndValue_returnsCorrectValue() {
        Map<String, String> map = createMapWith1Entry();
        String output = map.put(KEY, VAL);
        assertThat(output).isEqualTo(VAL);
    }

    @Test
    void remove_returnsCorrectValue() {
        Map<String, String> map = createMapWith1Entry();
        String output = map.remove(KEY);
        assertThat(output).isEqualTo(VAL);
    }

    @Test
    void remove_newKey_returnsNull() {
        Map<String, String> map = createMapWith1Entry();
        String output = map.remove("someOtherKey");
        assertThat(output).isNull();
    }

    @Test
    void size_afterRemove_newKey_is1() {
        Map<String, String> map = createMapWith1Entry();
        map.remove("someOtherKey");
        assertThat(map).hasSize(1);
    }

    @Test
    void iterator_hasNext_returnsTrue() {
        Map<String, String> map = createMapWith1Entry();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        assertThat(iterator).hasNext();
    }

    @Test
    void iterator_hasNext_twice_returnsTrue() {
        Map<String, String> map = createMapWith1Entry();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        iterator.hasNext();
        assertThat(iterator).hasNext();
    }

    @Test
    void iterator_yieldsCorrectEntry() {
        Map<String, String> map = createMapWith1Entry();
        assertThat(map).containsExactly(entry(KEY, VAL));
    }

    @Test
    void iterator_next_afterExhausted_throwsNoSuchElement() {
        Map<String, String> map = createMapWith1Entry();
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        exhaust(iterator);
        assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);
    }

    /** ------------------------------ END OF MAP WITH ONE ENTRY TESTS ---------------------------------**/



    /** ----------------------------------- TESTS OF DUPLICATE KEYS ------------------------------------**/
    Map<String, String> createMapAfterPutAndRemoveDuplicateKey() {
        Map<String, String> map = createTreeMap();
        map.put(KEY, OLD_VAL);
        map.put(KEY, VAL);
        map.remove(KEY);
        return map;
    }

    @Test
    void containsKey_withRemovedKey_returnsFalse() {
        Map<String, String> map = createMapAfterPutAndRemoveDuplicateKey();
        assertThat(map).doesNotContainKey(KEY);
    }

    @Test
    void get_removedKey_returnsNull() {
        Map<String, String> map = createMapAfterPutAndRemoveDuplicateKey();
        String output = map.get(KEY);
        assertThat(output).isNull();
    }

    @Test
    void put_removedKey_returnsNull() {
        Map<String, String> map = createMapAfterPutAndRemoveDuplicateKey();
        String output = map.put(KEY, VAL);
        assertThat(output).isNull();
    }

    @Test
    void remove_removedKey_returnsNull() {
        Map<String, String> map = createMapAfterPutAndRemoveDuplicateKey();
        String output = map.remove(KEY);
        assertThat(output).isNull();
    }

    /** ----------------------------------- END OF TESTS OF DUPLICATE KEYS -----------------------------**/



    /** ----------------------------------- TESTS OF MAP WITH 12 ENTRIES -------------------------------**/

    Map<Integer, Integer> setUpBinarySearchTreeMap() {
        Map<Integer, Integer> map = createTreeMap();
        map.put(36, 3);
        map.put(11, 4);
        map.put(17, 5);
        map.put(1, 6);
        map.put(14, 7);
        map.put(16, 8);
        map.put(18, 9);
        map.put(34, 11);
        map.put(72, 12);
        map.put(100, 13);
        return map;
    }

    @Test
    void size_10entries_is10() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        assertThat(map).hasSize(10);
    }

    @Test
    void containsKey_onEachKey_10entries_returnsTrue() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        Set<Integer> setOfKeys = map.keySet();
        Iterator<Integer> iterator = setOfKeys.iterator();
        while (iterator.hasNext()) {
            Integer key = iterator.next();
            assertThat(map).as("key: " + key).containsKey(key);
        }
    }

    @Test
    void containsKey_withNewKey_10entries_returnsFalse() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        assertThat(map).doesNotContainKey(-1);
    }

    @Test
    void get_10_entries_returnsCorrectValue() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        Integer output = map.get(14);
        assertThat(output).isEqualTo(7);
    }

    @Test
    void get_newKey_10_entries_returnsNull() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        Integer output = map.get(-1);
        assertThat(output).isNull();
    }

    @Test
    void put_newKey_10_entries_returnsNull() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        Integer output = map.put(5, 25);
        assertThat(output).isNull();
    }

    @Test
    void put_sameKey_10entries_returnsCorrectValue() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        Integer output = map.put(14, 9);
        assertThat(output).isEqualTo(7);
    }

    @Test
    void remove_1entries_returnsCorrectValue() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        Integer output = map.remove(11);
        assertThat(output).isEqualTo(4);
    }

    @Test
    void iterator_10entries_yieldsCorrectEntries() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());

        assertThat(map).containsExactlyInAnyOrderEntriesOf(Map.of(
                36, 3,
                11, 4,
                17, 5,
                1, 6,
                14, 7,
                16, 8,
                18, 9,
                34, 11,
                72, 12,
                100, 13
        ));
    }

    @Test
    void iterator_next_after_removing_all_entries() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        exhaust(iterator);
        assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void iterator_afterRemovingNewestKey_Yields_Correct_entries() {
        Map<Integer, Integer> map = setUpBinarySearchTreeMap();
        map.remove(100);
        assertThat(map).containsExactlyInAnyOrderEntriesOf(Map.of(
                36, 3,
                11, 4,
                17, 5,
                1, 6,
                14, 7,
                16, 8,
                18, 9,
                34, 11,
                72, 12
        ));
    }

    /** ---------------------------- END OF TESTS OF MAP WITH 12 ENTRIES -------------------------------**/
}
