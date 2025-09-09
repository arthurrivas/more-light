package br.com.more_light.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ticket_status")
@Getter
@Setter
@NoArgsConstructor
public class TicketStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(length = 30, unique = true, nullable = false)
    private String name;

    @Column(length = 10, unique = true, nullable = false)
    private String code;

    @Column(length = 255)
    private String description;

    public TicketStatus(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }
}

