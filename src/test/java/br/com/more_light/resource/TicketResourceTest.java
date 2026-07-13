package br.com.more_light.resource;

import br.com.more_light.domain.Account;
import br.com.more_light.domain.Ticket;
import br.com.more_light.dto.TicketDTO;
import br.com.more_light.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link TicketResource}. TicketService is mocked and the resource is
 * invoked directly (no Spring context, no HTTP layer) since this project isn't an MVC app.
 */
@ExtendWith(MockitoExtension.class)
class TicketResourceTest {

    @Mock
    private TicketService ticketService;

    private TicketResource ticketResource;

    private Account loggedInAccount;
    private Ticket ticket;
    private TicketDTO ticketDTO;

    @BeforeEach
    void setUp() {
        ticketResource = new TicketResource(ticketService);

        loggedInAccount = new Account();
        loggedInAccount.setId(1L);
        loggedInAccount.setUsername("arthur");
        loggedInAccount.setEmail("arthur@example.com");
        loggedInAccount.setPassword("secret");

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setDescription("Lampada queimada na rua principal");

        ticketDTO = new TicketDTO();
        ticketDTO.setId(1L);
        ticketDTO.setDescription("Lampada queimada na rua principal");
    }

    @Test
    void createTicket_returnsCreatedTicket() {
        when(ticketService.fromDTO(any(TicketDTO.class))).thenReturn(ticket);
        when(ticketService.toDTO(any(Ticket.class))).thenReturn(ticketDTO);

        ResponseEntity<TicketDTO> response = ticketResource.createTicket(ticketDTO, loggedInAccount);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getDescription()).isEqualTo("Lampada queimada na rua principal");
        verify(ticketService).save(ticket);
    }

    @Test
    void getTicketById_returnsTicket_whenFound() {
        when(ticketService.getTicketById(1L)).thenReturn(Optional.of(ticket));
        when(ticketService.toDTO(ticket)).thenReturn(ticketDTO);

        ResponseEntity<TicketDTO> response = ticketResource.getTicketById(1L);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void getTicketById_returns404_whenNotFound() {
        when(ticketService.getTicketById(99L)).thenReturn(Optional.empty());

        ResponseEntity<TicketDTO> response = ticketResource.getTicketById(99L);

        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void assignAgent_returnsUpdatedTicket() {
        when(ticketService.assignAgent(1L, 2L)).thenReturn(ticket);
        when(ticketService.toDTO(ticket)).thenReturn(ticketDTO);

        ResponseEntity<TicketDTO> response = ticketResource.assignAgent(1L, 2L);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void updateStatus_returnsUpdatedTicket() {
        br.com.more_light.domain.TicketStatus resolvedStatus =
                new br.com.more_light.domain.TicketStatus("Em andamento", "IN_PROGRESS", "Chamado em andamento");
        resolvedStatus.setId(2);

        when(ticketService.updateStatus(eq(1L), eq(2))).thenReturn(ticket);
        when(ticketService.toDTO(ticket)).thenReturn(ticketDTO);

        ResponseEntity<TicketDTO> response = ticketResource.updateStatus(1L, resolvedStatus);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getId()).isEqualTo(1L);
    }

    @Test
    void getPagedWithFilters_returnsPageOfTickets() {
        Page<Ticket> page = new PageImpl<>(List.of(ticket), PageRequest.of(0, 10), 1);
        when(ticketService.findAllPagedWithFilters(eq(1L), eq(2L), any())).thenReturn(page);
        when(ticketService.toDTO(ticket)).thenReturn(ticketDTO);

        ResponseEntity<Page<TicketDTO>> response =
                ticketResource.getPagedWithFilters(1L, 2L, PageRequest.of(0, 10));

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody().getTotalElements()).isEqualTo(1);
        assertThat(response.getBody().getContent().get(0).getId()).isEqualTo(1L);
    }
}
