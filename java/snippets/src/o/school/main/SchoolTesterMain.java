package o.school.main;

import o.school.common.Person;
import o.school.faculty.ScienceTeacher;
import o.school.iface.Evaluable;
import o.school.staff.Principal;
import o.school.students.Student;

public class SchoolTesterMain {
    public static void main(String... args) {
        ScienceTeacher beto = new ScienceTeacher("Albert","Einstein",29);
        System.out.println(beto.toString());
        System.out.println(beto.reportToBoss());
        beto.prepareClass(); 
        beto.prepareExam();
        System.out.println("Performance code: " + beto.getPerformanceCode()); 
        System.out.println("Evaluation Date: " + beto.getEvaluationDate());
        System.out.println("Exam - Q:" + beto.getExamQuestions());
        System.out.println();

        Person homero = new Principal("Homer","Simpson");
        System.out.println(homero.toString());
        Student pepito = new Student("Pepito","Pinolillo");
        System.out.println(pepito.toString());

        Evaluable e = beto;
        System.out.println("beto performance: " + e.getPerformanceCode());
//        e = homero;
//        System.out.println("homero performance: " + homero.getPerformanceCode()); //does not compile
        e = pepito;
        System.out.println("pepito performance: " + e.getPerformanceCode());
        System.out.println();
        System.out.println("Person counter: " + Person.getCounter());
    }

}

