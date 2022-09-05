package Model.ADT;

import java.util.Collection;
import java.util.Map;

public interface MyIDict<K, V> {

    void add(K key, V value);
    void update(K key, V value);
    V lookup(K key);
    boolean isDefined(K key);
    String toString();
    void delete(K key);

    Collection<V> getValues();
    MyIDict<K, V> cloneDict();

    void setContent(Map<K, V> newContent);
    Map<K, V> getContent();
}
