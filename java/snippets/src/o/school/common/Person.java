package o.school.common;

public class Person {
    public String _name;
    public String _lastname;
    private static int _counter;

    protected Person(String name, String lastname) {
        this._counter++;
        this._name = name;
        this._lastname = lastname;
    }

    public String toString() {
        return _lastname +", " + _name;
    }

    public static int getCounter(){
        return _counter;
    }

}

