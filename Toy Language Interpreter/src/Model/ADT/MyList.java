package Model.ADT;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements MyIList<T>{

    List<T> myList;

    public MyList()
    {
        this.myList = new ArrayList<T>();
    }

    @Override
    public void add(T element)
    {
        myList.add(element);
    }

    @Override
    public void remove(T element)
    {
        myList.remove(element);
    }

    @Override
    public boolean isEmpty()
    {
        return myList.isEmpty();
    }

    @Override
    public T getElementFromPosition(int position)
    {
        return myList.get(position);
    }

    @Override
    public int size() {
        return this.myList.size();
    }

    @Override
    public String toString()
    {
        return this.myList.toString();
    }

    @Override
    public void setContent(List<T> newList) {
        this.myList.clear();
        this.myList.addAll(newList);
    }

    @Override
    public List<T> getContent() {
        return this.myList;
    }
}
