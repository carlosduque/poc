package o.hibernate.one2many.simple;
 
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 
public class MainDeptEmployee {
 
    public static void main(String[] args) {
        Configuration conf = new Configuration();
        SessionFactory factory = conf.configure().buildSessionFactory();

        Session session = factory.openSession();
        session.beginTransaction();

        Department department = new Department();
        department.setDepartmentName("Sales");
        session.save(department);
 
        Employee emp1 = new Employee("Nina");
        Employee emp2 = new Employee("Tony");
 
        emp1.setDepartment(department);
        emp2.setDepartment(department);
 
        session.save(emp1);
        session.save(emp2);
 
        session.getTransaction().commit();
        session.close();
    }
}