package o.school.staff;

import o.school.common.Employee;

public class Principal extends Employee {

    public Principal(String name, String lastname) {
        super(name, lastname, 10500,666);
    }

    public float calculateTotalSalary() {
        float HEADMASTER_BONUS = 13635.0F;
        return HEADMASTER_BONUS + (this._salary * 12);
    }

    public String reportToBoss() {
        return "Board Report";
    }

}
