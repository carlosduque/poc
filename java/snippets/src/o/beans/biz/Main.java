package o.beans.biz;

import java.util.Date;

import o.beans.biz.Person;

public class Main {

    public static void main(String[] args) {
        Junior j = new Junior("Jun", "Ior", new Date(System.currentTimeMillis()));
        Senior s = new Senior("Sen", "Ior", new Date(System.currentTimeMillis()));
        Expert e = new Expert("Ex", "Pert", new Date(System.currentTimeMillis()));
        Manager m = new Manager("Ma", "Nager", new Date(System.currentTimeMillis()));
        Employee emp = new Employee("Em", "Ployee", new Date(System.currentTimeMillis()));
        Person p = new Person("Per", "Son", new Date(System.currentTimeMillis()));

        System.out.println(j.toString());
        System.out.println(s.toString());
        System.out.println(e.toString());
        System.out.println(m.toString());
        System.out.println(emp.toString());
        System.out.println(p.toString());
    }

}
