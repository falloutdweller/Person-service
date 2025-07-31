package telran.java58.person.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    private int id;
    @Setter
    private String name;
    private LocalDate birthDate;
    @Setter
    @Embedded
    private Address address;
}