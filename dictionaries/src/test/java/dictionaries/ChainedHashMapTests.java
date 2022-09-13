package dictionaries;

import java.util.Map;

public class ChainedHashMapTests extends BaseTreeTests {
    @Override
    protected <K extends Comparable<? super K>, V> Map<K, V> createTreeMap() {
        return new ChainedHashMap<>();
    }
}
