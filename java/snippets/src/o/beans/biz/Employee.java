package o.beans.biz;

import java.util.Date;

import o.beans.biz.Person;

public class Employee extends Person {

    public Employee(String name, String lastname, Date dob) {
        super(name, lastname, dob);
    }

    public String toString() {
        //return super.toString() + " id=" + id;
        return "name=" + getName() + " lastname=" + getLastname() + " DOB=" + getDayOfBirth() + " id=" + id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    protected long id = 0L;

}
