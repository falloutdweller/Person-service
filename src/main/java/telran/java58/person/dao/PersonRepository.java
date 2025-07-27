package telran.java58.person.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import telran.java58.person.dto.CityPopulationDto;
import telran.java58.person.model.Person;

import java.time.LocalDate;
import java.util.stream.Stream;

@Transactional(readOnly = true)
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Stream<Person> findPersonsByName(String name);

    Stream<Person> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    Stream<Person> findPersonsByAddressCity(String city);

    @Query("select distinct new telran.java58.person.dto.CityPopulationDto(p.address.city, count(p)) from Person p group by p.address.city")
    Stream<CityPopulationDto> findCityPopulation();

}
