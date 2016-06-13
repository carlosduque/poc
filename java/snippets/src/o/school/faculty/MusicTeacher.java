package o.school.faculty;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import o.school.iface.Evaluable;
import o.school.type.PerformanceType;

public class MusicTeacher extends Teacher implements Evaluable {

    private PerformanceType _pT;

    public MusicTeacher(String name, String lastname, int number) {
        super(name, lastname, number);
        _pT = PerformanceType.A;
    }

    void prepareClass() {
        System.out.println("Music class prepared");
    }

    void prepareExam() {
        exam = new Exam(setupQuestions(), setupAnswers());
    }

    public PerformanceType getPerformanceCode() {
        return _pT;
    }

    public Date getEvaluationDate() {
        return new Date();
    }

    private List setupQuestions() {
        ArrayList q = new ArrayList();
        q.add("La flauta es un instrumento de: percusion, cuerdas o viento.");
        q.add("Cual es el mejor grupo de rock del mundo.");
        q.add("Quien de estos genios quedo sordo: Mozart, Tchaikovsky, Korsakov, Beethoven");
        return q;
    }

    private List setupAnswers() {
        ArrayList a = new ArrayList();
        a.add("viento");
        a.add("Nirvana");
        a.add("Beethoven");        
        return a;
    }
}

