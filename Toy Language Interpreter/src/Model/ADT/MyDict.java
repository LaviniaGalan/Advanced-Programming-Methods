package Model.ADT;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyDict<K,V> implements MyIDict<K,V> {

    Map<K,V> myDict;

    public MyDict()
    {
        this.myDict = new HashMap<K, V>();
    }

    public MyDict(Map<K, V> map) { this.myDict = map;}

    @Override
    public void add(K key, V value)
    {
        this.myDict.put(key, value);
    }

    @Override
    public void update(K key, V value)
    {
        this.myDict.replace(key, value);
    }

    @Override
    public V lookup(K key)
    {
        return this.myDict.get(key);
    }

    @Override
    public boolean isDefined(K key)
    {
        return this.myDict.containsKey(key);
    }

    @Override
    public void delete(K key)
    {
        this.myDict.remove(key);
    }

    @Override
    public String toString()
    {
        return this.myDict.toString();
    }

    @Override
    public Collection<V> getValues()
    {
        return this.myDict.values();
    }

    @Override
    public Map<K, V> getContent()
    {
        return this.myDict;
    }

    @Override
    public void setContent(Map<K,V> newContent)
    {
        this.myDict.putAll(newContent);
    }

    @Override
    public MyIDict<K, V> cloneDict() {
        return new MyDict(this.getContent());
    }
}
