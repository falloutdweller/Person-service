package telran.java58.person.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import telran.java58.person.dto.AddressDto;
import telran.java58.person.dto.CityPopulationDto;
import telran.java58.person.dto.PersonDto;
import telran.java58.person.service.PersonService;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPerson(@RequestBody PersonDto person) {
        personService.addPerson(person);
    }

    @GetMapping("/{id}")
    public PersonDto getPerson(@PathVariable int id) {
        return personService.getPerson(id);
    }

    @DeleteMapping("/{id}")
    public PersonDto deletePerson(@PathVariable int id) {
        return personService.deletePerson(id);
    }

    @PatchMapping("{id}/name/{name}")
    public PersonDto updatePersonName(@PathVariable Integer id, @PathVariable String name) {
        return personService.updatePersonName(id, name);
    }

    @PatchMapping("{id}/address")
    public PersonDto updatePersonAddress(@PathVariable Integer id, @RequestBody AddressDto address) {
        return personService.updatePersonAddress(id, address);
    }

    @GetMapping("/name/{name}")
    public PersonDto[] findPersonsByName(@PathVariable String name) {
        return personService.findPersonsByName(name);
    }

    @GetMapping("/city/{city}")
    public PersonDto[] findPersonsByCity(@PathVariable String city) {
        return personService.findPersonsByCity(city);
    }

    @GetMapping("/ages/{minAge}/{maxAge}")
    public PersonDto[] findPersonsBetweenAge(@PathVariable Integer minAge, @PathVariable Integer maxAge) {
        return personService.findPersonsBetweenAge(minAge, maxAge);
    }

    @GetMapping("/population/city")
    public Iterable<CityPopulationDto> getCityPopulation() {
        return personService.getCityPopulation();
    }
}
