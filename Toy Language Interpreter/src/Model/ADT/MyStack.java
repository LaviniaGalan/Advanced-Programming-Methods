package Model.ADT;

import Exceptions.EmptyStack;

import java.util.*;
import java.util.stream.Collector;

public class MyStack<T> implements MyIStack<T> {
    Deque<T> myStack;

    public MyStack()
    {
      this.myStack = new ArrayDeque<>();
    }

    @Override
    public void push(T value)
    {
        myStack.push(value);
    }

    @Override
    public T pop() throws Exception{
        if (this.myStack.isEmpty())
            throw new EmptyStack("The stack is empty.");
        return myStack.pop();
    }

    @Override
    public boolean isEmpty()
    {
        return myStack.isEmpty();
    }

    @Override
    public String toString()
    {
        return myStack.toString();
    }

    @Override
    public List<T> getContent() {
        List<T> stackContents = new ArrayList<T>(this.myStack);
        return stackContents;
    }
}
