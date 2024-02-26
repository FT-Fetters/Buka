package xyz.ldqc.buka.data.repository.core.engine.query;

import com.alibaba.fastjson2.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * @author Fetters
 */
public class SieveBuilder {

    private final Map<String, Entry<String, Conditional>> conditionalMap = new HashMap<>();

    private SieveBuilder() {
    }

    public static SieveBuilder get() {
        return new SieveBuilder();
    }

    public SieveBuilder set(String orgName, String tgName,
        Function<ConditionalConstruct, Conditional> constructFunction) {
        Conditional conditional = constructFunction.apply(new ConditionalConstruct());
        return set(orgName, tgName, conditional);
    }

    public SieveBuilder set(String orgName, String tgName, Conditional conditional) {
        ConditionalEntry<String, Conditional> entry = new ConditionalEntry<>(tgName, conditional);
        conditionalMap.put(orgName, entry);
        return this;
    }



    public Sieve build() {
        return new Sieve(this.conditionalMap);
    }

    private static class ConditionalEntry<K, V> implements Entry<K, V> {

        private final K key;

        private V val;

        public ConditionalEntry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return val;
        }

        @Override
        public V setValue(V value) {
            this.val = value;
            return this.val;
        }

    }

}
