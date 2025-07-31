package telran.java58.person.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Employee extends Person{
    private String company;
    private int salary;

    public Employee(int id, String name, LocalDate birthDate, Address address, String company, int salary) {
        super(id, name, birthDate, address);
        this.company = company;
        this.salary = salary;
    }
}
