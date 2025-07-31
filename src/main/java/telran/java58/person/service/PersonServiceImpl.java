package telran.java58.person.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.java58.person.dao.PersonRepository;
import telran.java58.person.dto.*;
import telran.java58.person.dto.exception.PersonExistsException;
import telran.java58.person.dto.exception.PersonNotFoundException;
import telran.java58.person.model.Address;
import telran.java58.person.model.Child;
import telran.java58.person.model.Employee;
import telran.java58.person.model.Person;

import java.time.LocalDate;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, CommandLineRunner {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public void addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            throw new PersonExistsException();
        }
        if (personDto instanceof EmployeeDto) {
            personRepository.save(modelMapper.map(personDto, Employee.class));
            return;
        }
        if (personDto instanceof ChildDto) {
            personRepository.save(modelMapper.map(personDto, Child.class));
            return;
        }
        personRepository.save(modelMapper.map(personDto, Person.class));
    }

    @Override
    public PersonDto getPerson(int id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
if (person instanceof Child) {
    return modelMapper.map(person, ChildDto.class);
}
if (person instanceof Employee) {
    return modelMapper.map(person, EmployeeDto.class);
}
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto deletePerson(int id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        personRepository.delete(person);
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto updatePersonName(Integer id, String newName) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        if (newName != null) {
            person.setName(newName);
        }
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional
    public PersonDto updatePersonAddress(Integer id, AddressDto newAddress) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        Address personAddress = person.getAddress();
        if (newAddress.getCity() != null) {
            personAddress.setCity(newAddress.getCity());
        }
        if (newAddress.getStreet() != null) {
            personAddress.setStreet(newAddress.getStreet());
        }
        if (newAddress.getBuilding() != null) {
            personAddress.setBuilding(newAddress.getBuilding());
        }
        person.setAddress(modelMapper.map(personAddress, Address.class));
        return modelMapper.map(person, PersonDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto[] findPersonsByName(String name) {
        return personRepository.findPersonsByName(name)
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto[] findPersonsByCity(String city) {
        return personRepository.findPersonsByAddressCity(city)
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto[] findPersonsBetweenAge(Integer minAge, Integer maxAge) {
        LocalDate startDate = LocalDate.now().minusYears(maxAge);
        LocalDate endDate = LocalDate.now().minusYears(minAge);
        return personRepository.findByBirthDateBetween(startDate, endDate)
                .map(person -> modelMapper.map(person, PersonDto.class))
                .toArray(PersonDto[]::new);
    }

    @Override
    public Iterable<CityPopulationDto> getCityPopulation() {
        return personRepository.findCityPopulation();
    }

    @Override
    public void run(String... args) {
        if (personRepository.count() == 0) {
            Person person = new Person(1000, "John", LocalDate.of(1985, 3, 11),
                    new Address("Tel Aviv", "Ben Gvirol", 81));
            Child child = new Child(2000, "Peter", LocalDate.of(2019, 7, 5),
                    new Address("Ashkelon", "Bar Kohva", 21), "Shalom");
            Employee employee = new Employee(3000, "Mary", LocalDate.of(1995, 11, 23),
                    new Address("Rehovot", "Ben Herzl", 7), "Microsoft", 20_000);
            personRepository.saveAll(Arrays.asList(person, child, employee));
        }
    }
}
