package br.com.more_light.dto;

import br.com.more_light.domain.Person;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class AccountDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private PersonDTO personDTO;
}