package Model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyHeap<K,V> implements MyIHeap<K,V>{

    Map<K,V> myHeap;
    int freeLocation;

    public MyHeap()
    {
        this.myHeap = new HashMap<K, V>();
        this.freeLocation = 1;
    }

    @Override
    public void add(K key, V value)
    {
        this.myHeap.put(key, value);
    }

    @Override
    public void update(K key, V value)
    {
        this.myHeap.replace(key, value);
    }

    @Override
    public V lookup(K key)
    {
        return this.myHeap.get(key);
    }

    @Override
    public boolean isDefined(K key)
    {
        return this.myHeap.containsKey(key);
    }

    @Override
    public void delete(K key)
    {
        this.myHeap.remove(key);
    }


    @Override
    public int getFreeLocation() {
        this.freeLocation++;
        return this.freeLocation - 1;
    }

    @Override
    public Map<K, V> getContent()
    {
        return this.myHeap;
    }

    @Override
    public void setContent(Map<K, V> newMap)
    {
        this.myHeap = newMap;
    }

    @Override
    public String toString()
    {
        return this.myHeap.toString();
    }

    @Override
    public Collection<V> getValues() {
        return this.myHeap.values();
    }
}
