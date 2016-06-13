package o.school.faculty;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import o.school.iface.Evaluable;
import o.school.type.PerformanceType;

public class ScienceTeacher extends Teacher implements Evaluable {

    private PerformanceType _pT;

    public ScienceTeacher(String name, String lastname, int number) {
        super(name, lastname, number);
        _pT = PerformanceType.B;
    }

    public void prepareClass() {
        System.out.println("Science class prepared");
    }

    public void prepareExam() {
        exam = new Exam(setupQuestions(), setupAnswers());
    }

    public PerformanceType getPerformanceCode() {
        return _pT;
    }

    public Date getEvaluationDate() {
        return new Date();
    }

    public List getExamQuestions() {
        List list = new ArrayList();
        list.add(this.exam.getQuestion(0));
        list.add(this.exam.getQuestion(1));
        list.add(this.exam.getQuestion(2));
        return list;
    }

    private List setupQuestions() {
        ArrayList q = new ArrayList();
        q.add("1+1=");
        q.add("Cual es el numero PI");
        q.add("Cual era la mascota que uso Schroedinger para su paradoja");
        return q;
    }

    private List setupAnswers() {
        ArrayList a = new ArrayList();
        a.add("3");
        a.add("3.141592");
        a.add("Gato");
        return a;
    }
}

