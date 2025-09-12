package br.com.more_light.resource;

import br.com.more_light.domain.Account;
import br.com.more_light.domain.Ticket;
import br.com.more_light.domain.TicketStatus;
import br.com.more_light.dto.TicketDTO;
import br.com.more_light.service.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
        Ticket ticket = ticketService.assignAgent(id, agentId);
        TicketDTO ticketDTO = ticketService.toDTO(ticket);
        return ResponseEntity.ok(ticketDTO);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketDTO> updateStatus(@PathVariable Long id, @RequestParam TicketStatus status) {
        Ticket updatedTicket = ticketService.updateStatus(id, status.getId());
        TicketDTO ticketDTO = ticketService.toDTO(updatedTicket);
        return ResponseEntity.ok(ticketDTO);
    }

    @GetMapping
    public ResponseEntity<Page<TicketDTO>> getPagedWithFilters(
            @RequestParam Long idAgent,
            @RequestParam Long idCreator,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Ticket> tickets = ticketService.findAllPagedWithFilters(idAgent, idCreator,  pageable);
        Page<TicketDTO> ticketDTOS = tickets.map(ticketService::toDTO);
        return ResponseEntity.ok(ticketDTOS);
    }


    @GetMapping("/created-by/{idCreator}")
    public ResponseEntity<Page<TicketDTO>> getPagedWithByCreator(
            @RequestParam Long idCreator,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return getPagedWithFilters(null, idCreator, pageable);
    }
}

