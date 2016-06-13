package o.school.faculty;

import o.school.common.Employee;

public abstract class Teacher extends Employee {
    protected Exam exam;

    Teacher(String name, String lastname, int number) {
        super(name, lastname, 3000, number);
    }

    public String reportToBoss() {
        return "Class schedule report";
    }

    abstract void prepareClass();
    abstract void prepareExam();

}

