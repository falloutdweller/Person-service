package telran.java58.person.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import telran.java58.person.dto.ChildDto;
import telran.java58.person.dto.EmployeeDto;
import telran.java58.person.dto.PersonDto;
import telran.java58.person.model.Child;
import telran.java58.person.model.Employee;
import telran.java58.person.model.Person;

@Component
@RequiredArgsConstructor
public class PersonMappingService {
    private final ModelMapper modelMapper;

    public Person dtoToEntity(PersonDto person) {
        if (person instanceof ChildDto) {
            return modelMapper.map(person, Child.class);
        }
        if (person instanceof EmployeeDto) {
            return modelMapper.map(person, Employee.class);
        }
        return modelMapper.map(person, Person.class);
    }

    public PersonDto entityToDto(Person person) {
        if (person instanceof Child) {
            return modelMapper.map(person, ChildDto.class);
        }
        if (person instanceof Employee) {
            return modelMapper.map(person, EmployeeDto.class);
        }
        return modelMapper.map(person, PersonDto.class);
    }

}
