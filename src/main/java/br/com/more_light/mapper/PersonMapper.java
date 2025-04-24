package br.com.more_light.mapper;

import br.com.more_light.domain.Person;
import br.com.more_light.dto.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "birthday", source = "birthday")
    Person personDtoToPerson(PersonDTO personDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "birthday", source = "birthday")
    PersonDTO personToPersonDTO(Person person);

}