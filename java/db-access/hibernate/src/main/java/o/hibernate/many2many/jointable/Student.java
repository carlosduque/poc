package o.hibernate.many2many.jointable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Student implements Serializable {

    private static final long serialVersionUID = 2318586038723509107L;

    private Integer studentId;
    private String studentName;
    private Set<Course> courses = new HashSet<Course>(0);

    public Student() { }

    public Student(Integer id, String name) {
        super();
        this.studentId = id;
        this.studentName = name;
    }

    @Override
    public String toString() {
        return String.format("Student [studentId=%s, studentName=%s]", studentId, studentName);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

}
