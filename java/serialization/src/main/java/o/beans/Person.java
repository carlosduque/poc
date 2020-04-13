package o.beans;

import java.util.Date;

public class Person {

    private final String name;
    private final String lastname;
    private final Date dob;

    public Person(String name, String lastname, Date dob) {
        this.name = name;
        this.lastname = lastname;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getDayOfBirth() {
        return dob;
    }

    public String toString() {
        return "name=" + this.name + " lastname=" + this.lastname + " DOB=" + this.dob;
    }
}
