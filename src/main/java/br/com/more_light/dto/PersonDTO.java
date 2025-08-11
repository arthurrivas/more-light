package br.com.more_light.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;


@Getter
@Setter
public class PersonDTO {
    private Long id;

    @NotBlank
    private String name;

    @CPF
    private String cpf;

    private Date birthday;
}