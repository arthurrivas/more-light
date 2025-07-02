// Local: com/seupacote/projeto/domain/Role.java
package br.com.more_light.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 20, unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }
}