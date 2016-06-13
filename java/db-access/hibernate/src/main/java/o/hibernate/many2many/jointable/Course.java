package o.hibernate.many2many.jointable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Course implements Serializable {

    private static final long serialVersionUID = 1688488165546183087L;

    private Integer courseId;
    private String courseDescription;
    private Set<Student> students = new HashSet<Student>(0);

    public Course() { }

    public Course(Integer id, String description) {
        super();
        this.courseId = id;
        this.courseDescription = description;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Course [courseId=").append(courseId)
               .append(", courseDescription=").append(courseDescription).append("]");
        return builder.toString();
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void removeStudent(Student student) {
        this.students.remove(student);
    }
}
