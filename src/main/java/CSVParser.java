import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class CSVParser {
    public static void main(String[] args){
        Path file_path = Paths.get("src/main/resources/EmployeesData.csv");
        List<String> headers= List.of("id,name,age,department,salary");

        try(Stream<String> line = Files.lines(file_path)){
            List<Employee> employees = line.skip(1)
                    .map(data -> data.split(","))
                    .map(data -> new Employee(
                            Integer.parseInt(data[0].trim()),
                            data[1].trim(),
                            Integer.parseInt(data[2].trim()),
                            data[3].trim(),
                            Double.parseDouble(data[4].trim())
                    ))
                    .toList();

            List<String> Engineers = employees.stream()
                    .filter(employee -> employee.getDepartment().equalsIgnoreCase("Engineering"))
                    .map(emp -> String.format("%d,%s,%d,%s,%.2f",
                            emp.getId(), emp.getName(), emp.getAge(), emp.getDepartment(), emp.getSalary()))
                    .toList();

            List<String> Seniors = employees.stream()
                    .filter(employee -> employee.getAge() > 30)
                    .map(emp -> String.format("%d,%s,%d,%s,%.2f",
                            emp.getId(), emp.getName(), emp.getAge(), emp.getDepartment(), emp.getSalary()))
                    .toList();

            List<String> HigherPayEmployees = employees.stream()
                    .filter(employee -> employee.getSalary() >= 60000)
                    .map(emp -> String.format("%d,%s,%d,%s,%.2f",
                            emp.getId(), emp.getName(), emp.getAge(), emp.getDepartment(), emp.getSalary()))
                    .toList();

            List<String> file_paths = List.of("Engineers.csv","Seniors.csv","HigherPayEmployees.csv");

            for (String file : file_paths){
                Files.write(Paths.get(file), headers);
            }

            Files.write(Paths.get("Engineers.csv"), (Iterable<? extends CharSequence>) Engineers, StandardOpenOption.APPEND);
            Files.write(Paths.get("Seniors.csv"), (Iterable<? extends CharSequence>) Seniors, StandardOpenOption.APPEND);
            Files.write(Paths.get("HigherPayEmployees.csv"), (Iterable<? extends CharSequence>) HigherPayEmployees, StandardOpenOption.APPEND);

        }

        catch(IOException e){
            System.out.println(e);
        }

    }
}
