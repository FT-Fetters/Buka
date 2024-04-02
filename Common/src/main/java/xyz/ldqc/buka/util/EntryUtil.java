package xyz.ldqc.buka.util;

import java.util.Map.Entry;

/**
 * @author Fetters
 */
public class EntryUtil{

    private EntryUtil()
    {
    }

    public static <K, V> Entry<K, V> of(K key, V value)
    {
        return new MyEntry<>(key, value);
    }

    private static class MyEntry<K, V> implements Entry<K, V>{

        private K key;

        private V value;

        public MyEntry(){

        }

        public MyEntry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }
    }

}
