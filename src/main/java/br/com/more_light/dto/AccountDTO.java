package br.com.more_light.dto;

import br.com.more_light.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;

    @Length(min = 5, max = 50)
    private String username;
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private PersonDTO person;

    private Set<Role> roles = new HashSet<>();
}