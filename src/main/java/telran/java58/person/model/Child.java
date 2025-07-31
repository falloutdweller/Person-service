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
public class Child extends Person{
    private String kindergarten;

    public Child(int id, String name, LocalDate birthDate, Address address, String kindergarten) {
        super(id, name, birthDate, address);
        this.kindergarten = kindergarten;
    }

}
