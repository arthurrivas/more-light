package br.com.more_light.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
public class PersonDTO {
    private Long id;
    private String name;
    private String cpf;
    private Date birthday;
}