package o.beans.biz;

import java.util.Date;

public class Person {

    private String name;
    private String lastname;
    private String ssn;
    private Date dob;

    public Person(String nameVal, String lastnameVal, Date dobVal) {
        this(nameVal, lastnameVal, null, dobVal);
    }

    public Person(String nameVal, String lastnameVal, String ssnVal, Date dobVal) {
        this.name = nameVal;
        this.lastname = lastnameVal;
        this.ssn = ssnVal;
        this.dob = dobVal;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSsn() {
        return ssn;
    }

    public Date getDayOfBirth() {
        return dob;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    public void setDayOfBirth(Date dayOfBirth) {
        this.dob = dayOfBirth;
    }
}
