package Model.ADT;

import java.util.List;

public interface MyIList<T> {

    void add(T element);
    void remove(T element);
    boolean isEmpty();
    T getElementFromPosition(int position);
    int size();
    String toString();

    void setContent(List<T> newList);
    List<T> getContent();
}
