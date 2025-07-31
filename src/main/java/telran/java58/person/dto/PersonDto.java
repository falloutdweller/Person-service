package telran.java58.person.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChildDto.class, name = "child"),
        @JsonSubTypes.Type(value = EmployeeDto.class, name = "employee"),
        @JsonSubTypes.Type(value = PersonDto.class, name = "person")
})
public class PersonDto {
    private Integer id;
    private String name;
    private LocalDate birthDate;
    private AddressDto address;

}
