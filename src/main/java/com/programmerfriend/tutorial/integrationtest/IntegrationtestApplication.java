package com.programmerfriend.tutorial.integrationtest;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@EnableJpaRepositories
@SpringBootApplication
public class IntegrationtestApplication {


    public static void main(String[] args) {
        SpringApplication.run(IntegrationtestApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandlineRunner(EmployeeRepository employeeRepository) throws Exception {

        return args -> {
            employeeRepository.deleteAllInBatch();
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(createEmployee("First", "Last"));
            employees.add(createEmployee("Mr.", "Frost"));
            employees.add(createEmployee("Santa", "Clause"));
            employees.add(createEmployee("Peter", "Pan"));
            employees.add(createEmployee("Cinder", "ella"));
            employees.add(createEmployee("What", "ever"));
            employeeRepository.saveAll(employees);
        };
    }

    private Employee createEmployee(String firstName, String lastName) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        return employee;
    }


}

@Repository
interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

@RestController
@AllArgsConstructor
class EmployeeController {
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
}

@Data
@Entity
class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
}

