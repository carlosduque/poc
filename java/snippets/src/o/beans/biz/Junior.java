package o.beans.biz;

import java.util.Date;

public class Junior extends Employee {
    public Junior(String name, String lastname, Date dob) {
        super(name, lastname, dob);
    }

    public String toString() {
        //return super.toString() + " power=" + power;
        return "name=" + getName() + " lastname=" + getLastname() + " DOB=" + getDayOfBirth() + " id=" + id + " power=" + power;
    }
    private int power = 1;
}
