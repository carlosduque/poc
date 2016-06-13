package o.school.common;

public abstract class Employee extends Person {
    private final int BONUS = 3000;
    //protected: para que lo puedan ver los que no estan en este paquete
    protected float _salary;  
    private int _number;

    protected Employee(String name, String lastname, float salary, int number) {
        super(name, lastname);
        this._salary = salary;
        this._number = number;
    }

    public String toString() {
        return super.toString() + " employee number: " + this._number + " salary: " + this._salary;
    }

    public float calculateTotalSalary() {
        return BONUS + (this._salary * 12);
    }

    public abstract String reportToBoss();
}

