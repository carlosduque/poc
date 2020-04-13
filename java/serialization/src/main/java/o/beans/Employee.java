package o.beans;

import java.util.Date;

import o.beans.Person;

public class Employee extends Person {

    private final int employeeId;
    private transient final String role;

    public Employee(int id, String name, String lastname, Date dob, String role) {
        super(name, lastname, dob);
        this.employeeId = id;
        this.role = role;
    }

    public String toString() {
        return "Employee[employee id=" + this.employeeId + "@role=" + this.role + "::" + super.toString() + "]";
    }

    public int getId() {
        return this.employeeId;
    }

    public String getRole() {
        return this.role;
    }
}
