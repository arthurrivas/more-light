package br.com.more_light.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {

    private Long id;
    private PersonDTO person;
    private List<TicketDTO> tickets = new ArrayList<>();
}
