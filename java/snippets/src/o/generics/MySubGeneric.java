package o.generics;

public class MySubGeneric<T> extends MyGeneric<T> {
    private T element;
    public String def() {
        return "def: " + element.getClass().getName();
    }
    public void put(T t) {
        element = t;
    }
}
