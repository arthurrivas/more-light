package br.com.more_light.service;

import br.com.more_light.domain.Person;
import br.com.more_light.dto.PersonDTO;
import br.com.more_light.mapper.PersonMapper;
import br.com.more_light.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository personRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    public List<PersonDTO> getAllPersons() {
        return personRepository.findAll()
                .stream()
                .map(personMapper::personToPersonDTO)
                .collect(Collectors.toList());
    }

    public Person getById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    public PersonDTO createPerson(PersonDTO personDTO) {
        Person person = personMapper.personDtoToPerson(personDTO);
        person = personRepository.save(person);
        return personMapper.personToPersonDTO(person);
    }

    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(personDTO.getName());
        person.setCpf(personDTO.getCpf());
        person.setBirthday(personDTO.getBirthday());
        person = personRepository.save(person);
        return personMapper.personToPersonDTO(person);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public PersonDTO toDTO(Person person) {
        return personMapper.personToPersonDTO(person);
    }
}