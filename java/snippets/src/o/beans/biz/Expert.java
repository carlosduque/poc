package o.beans.biz;

import java.util.Date;

public class Expert extends Employee {
    public Expert(String name, String lastname, Date dob) {
        super(name, lastname, dob);
    }

    public String toString() {
        //return super.toString() + " power=" + power;
        return "name=" + getName() + " lastname=" + getLastname() + " DOB=" + getDayOfBirth() + " id=" + id + " power=" + power;
    }
    private int power = 5;
}
