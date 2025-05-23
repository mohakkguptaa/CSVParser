import lombok.Data;

@Data
public class Employee {
    int id;

    String name;

    int age;

    String department;

    double salary;

    public Employee(int id, String name, int age, String department, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
        this.salary = salary;
    }
}
