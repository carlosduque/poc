package o.school.students;

import java.util.Date;

import o.school.common.Person;
import o.school.iface.Evaluable;
import o.school.type.PerformanceType;

public class Student extends Person implements Evaluable {

    private PerformanceType _performanceType;
    private Date _evaluationDate;

    public Student(String name, String lastname) {
        super(name, lastname);
        this._performanceType = PerformanceType.F;
        this._evaluationDate = null;
    }

    public PerformanceType getPerformanceCode() {
        return this._performanceType;
    }

    public Date getEvaluationDate() {
        return this._evaluationDate;
    }

    public void setEvaluationDate(Date evaluationDate) {
        this._evaluationDate = evaluationDate;
    }

}
