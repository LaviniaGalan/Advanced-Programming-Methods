package Model.ADT;

import java.util.Collection;
import java.util.Map;

public interface MyIHeap<K, V> {
    void add(K key, V value);
    void update(K key, V value);
    V lookup(K key);
    boolean isDefined(K key);
    String toString();
    void delete(K key);
    int getFreeLocation();

    Map<K, V> getContent();
    void setContent(Map<K,V> newMap);
    Collection<V> getValues();
}
