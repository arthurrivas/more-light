package br.com.more_light.resource;

import br.com.more_light.domain.Person;
import br.com.more_light.dto.PersonDTO;
import br.com.more_light.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link PersonResource}. PersonService is mocked and the resource is
 * invoked directly (no Spring context, no HTTP layer) since this project isn't an MVC app.
 */
@ExtendWith(MockitoExtension.class)
class PersonResourceTest {

    @Mock
    private PersonService personService;

    private PersonResource personResource;

    private PersonDTO personDTO;
    private Person person;

    @BeforeEach
    void setUp() {
        personResource = new PersonResource(personService);

        personDTO = new PersonDTO();
        personDTO.setId(1L);
        personDTO.setName("Arthur Rivas");
        personDTO.setCpf("11144477735");

        person = new Person();
        person.setId(1L);
        person.setName("Arthur Rivas");
        person.setCpf("11144477735");
    }

    @Test
    void getAllPersons_returnsList() {
        when(personService.getAllPersons()).thenReturn(List.of(personDTO));

        ResponseEntity<List<PersonDTO>> response = personResource.getAllPersons();

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().get(0).getName()).isEqualTo("Arthur Rivas");
    }

    @Test
    void getPersonById_returnsPerson_whenExists() {
        when(personService.getById(1L)).thenReturn(person);
        when(personService.toDTO(person)).thenReturn(personDTO);

        ResponseEntity<PersonDTO> response = personResource.getPersonById(1L);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getName()).isEqualTo("Arthur Rivas");
    }

    @Test
    void getPersonById_propagatesException_whenNotFound() {
        when(personService.getById(99L)).thenThrow(new RuntimeException("Person not found"));

        assertThatThrownBy(() -> personResource.getPersonById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Person not found");
    }

    @Test
    void createPerson_returnsCreatedPerson() {
        when(personService.createPerson(any(PersonDTO.class))).thenReturn(personDTO);

        ResponseEntity<PersonDTO> response = personResource.createPerson(personDTO);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getName()).isEqualTo("Arthur Rivas");
    }

    @Test
    void updatePerson_returnsUpdatedPerson() {
        when(personService.updatePerson(eq(1L), any(PersonDTO.class))).thenReturn(personDTO);

        ResponseEntity<PersonDTO> response = personResource.updatePerson(1L, personDTO);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getName()).isEqualTo("Arthur Rivas");
    }

    @Test
    void deletePerson_returns204NoContent() {
        ResponseEntity<Void> response = personResource.deletePerson(1L);

        assertThat(response.getStatusCode().value()).isEqualTo(204);
        verify(personService).deletePerson(1L);
    }
}
