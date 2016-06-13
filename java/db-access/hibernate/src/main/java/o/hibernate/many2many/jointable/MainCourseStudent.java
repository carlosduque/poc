package o.hibernate.many2many.jointable;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainCourseStudent {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainCourseStudent.class);

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        SessionFactory factory = conf.configure().buildSessionFactory();
        insertData(factory);
        showData(factory);
    }

    private static void insertData(SessionFactory factory) {
        Session session = factory.openSession();
        try {
            Course math = new Course(null, "Mathematics");
            Course cs = new Course(null, "Computer Science");

            Student leonard = new Student(null, "Leonard");
            Student sheldon = new Student(null, "Sheldon");
            Student raj = new Student(null, "Rajesh");

            math.addStudent(leonard);
            math.addStudent(sheldon);
            cs.addStudent(raj);
            cs.addStudent(leonard);

            Transaction tx = session.beginTransaction();
            session.save(cs);
            session.save(math);
            tx.commit();

        } catch (HibernateException he) {
            LOGGER.error("Something failed, rolling back. " + he);
            if (session.beginTransaction() != null) {
                session.beginTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }
    private static void showData(SessionFactory factory) {
        Session session = factory.openSession();
        try {
            Query qry = null;
            qry = session.createQuery("FROM Course c where c.courseDescription = :description");
            qry.setParameter("description", "Computer Science");

            Course course = (Course) qry.uniqueResult();
            LOGGER.info("found: " + course);
            for (Student s : course.getStudents()) {
                LOGGER.info(" - " + s.getStudentName());
            }

            qry = session.createQuery("FROM Student s where s.studentId = :id");
            qry.setParameter("id", 2);
            Student student = (Student) qry.uniqueResult();
            LOGGER.info("found: " + student + ", " + student.getCourses());
        } catch (HibernateException he) {
            LOGGER.error("Something failed, rolling back. " + he);
            if (session.beginTransaction() != null) {
                session.beginTransaction().rollback();
            }
        } finally {
            session.close();
        }
    }

}
