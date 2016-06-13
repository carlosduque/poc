package o.generics;

public class MyGeneric<T> {
    private T element;

    public T get() {
        return element;
    }
    public void set(T t) {
        this.element = t;
    }

}
