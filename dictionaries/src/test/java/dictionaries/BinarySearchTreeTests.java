package dictionaries;

import java.util.Map;

public class BinarySearchTreeTests extends BaseTreeTests {
    @Override
    protected <K extends Comparable<? super K>, V> Map<K, V> createTreeMap() {
        return new BinarySearchTreeMap<>();
    }
}
