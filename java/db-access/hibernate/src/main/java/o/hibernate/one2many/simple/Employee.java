package o.hibernate.one2many.simple;

public class Employee {

    private Long employeeId;
    private String employeeName;
    private Department department;

    public Employee() { }

    public Employee(String name) {
        this.employeeName = name;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return employeeName;
    }

    public void setName(String name) {
        this.employeeName = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
