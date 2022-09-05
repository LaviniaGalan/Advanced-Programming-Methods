package Model.ADT;

import java.util.List;

public interface MyIStack<T> {

    void push(T value);
    T pop() throws Exception;
    boolean isEmpty();
    String toString();

    List<T> getContent();
}
