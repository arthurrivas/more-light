package br.com.more_light.resource;

import br.com.more_light.domain.Account;
import br.com.more_light.domain.Ticket;
import br.com.more_light.domain.TicketStatus;
import br.com.more_light.dto.TicketDTO;
import br.com.more_light.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketResource {

    private final TicketService ticketService;

    public TicketResource(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO, @AuthenticationPrincipal Account loggedInAccount) {

        Ticket newTicket = ticketService.fromDTO(ticketDTO);
        newTicket.setCreatedBy(loggedInAccount);
        ticketService.save(newTicket);

        return ResponseEntity.ok(ticketService.toDTO(newTicket));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id).map(ticketService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/assign/{agentId}")
    public ResponseEntity<TicketDTO> assignAgent(@PathVariable Long id, @PathVariable Long agentId) {
        return ResponseEntity.ok(ticketService.assignAgent(id, agentId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketDTO> updateStatus(@PathVariable Long id, @RequestParam TicketStatus status) {
        Ticket updatedTicket = ticketService.updateStatus(id, status.getId());
        TicketDTO ticketDTO = ticketService.toDTO(updatedTicket);
        return ResponseEntity.ok(ticketDTO);
    }
}

