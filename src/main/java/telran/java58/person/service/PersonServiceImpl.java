package telran.java58.person.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import telran.java58.person.dao.PersonRepository;
import telran.java58.person.dto.AddressDto;
import telran.java58.person.dto.CityPopulationDto;
import telran.java58.person.dto.PersonDto;
import telran.java58.person.dto.exception.PersonExistsException;
import telran.java58.person.dto.exception.PersonNotFoundException;
import telran.java58.person.model.Address;
import telran.java58.person.model.Person;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public void addPerson(PersonDto personDto) {
        if (personRepository.existsById(personDto.getId())) {
            throw new PersonExistsException();
        }
        personRepository.save(modelMapper.map(personDto, Person.class));
    }

    @Override
    public PersonDto getPerson(int id) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
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
        personRepository.save(person);
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
        person.setAddress(personAddress);
        personRepository.save(person);
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
    @Transactional(readOnly = true)
    public Iterable<CityPopulationDto> getCityPopulation() {
        return personRepository.findCityPopulation()
                .map(person -> modelMapper.map(person, CityPopulationDto.class))
                .collect(Collectors.toList());
    }
}
