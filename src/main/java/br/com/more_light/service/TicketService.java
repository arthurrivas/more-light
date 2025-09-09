package br.com.more_light.service;

import br.com.more_light.domain.Ticket;
import br.com.more_light.domain.TicketStatus;
import br.com.more_light.dto.TicketDTO;
import br.com.more_light.mapper.TicketMapper;
import br.com.more_light.repository.TicketRepository;
import br.com.more_light.repository.TicketStatusRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final TicketMapper ticketMapper;
    private final TicketStatusRepository ticketStatusRepository;

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper,
                         TicketStatusRepository ticketStatusRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.ticketStatusRepository = ticketStatusRepository;
    }

    public Ticket save(Ticket ticket) {
        ticketRepository.save(ticket);
        return ticket;
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public TicketDTO assignAgent(Long ticketId, Long agentId) {


        return null;
    }

    public Ticket updateStatus(Long ticketId, Integer statusId) {
        Ticket ticket = ticketRepository.getReferenceById(ticketId);
        TicketStatus newStatus = ticketStatusRepository.getReferenceById(statusId);
        ticket.setStatus(newStatus);
        save(ticket);
        return null;
    }

    public Ticket fromDTO(TicketDTO ticketDTO) {
        return ticketMapper.fromDto(ticketDTO);
    }

    public TicketDTO toDTO(Ticket ticket) {
        return ticketMapper.toDTO(ticket);
    }
}
