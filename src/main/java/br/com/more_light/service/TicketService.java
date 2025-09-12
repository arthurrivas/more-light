package br.com.more_light.service;

import br.com.more_light.domain.Ticket;
import br.com.more_light.domain.TicketStatus;
import br.com.more_light.dto.TicketDTO;
import br.com.more_light.mapper.TicketMapper;
import br.com.more_light.repository.AccountRepository;
import br.com.more_light.repository.TicketRepository;
import br.com.more_light.repository.TicketStatusRepository;
import br.com.more_light.repository.specification.TicketSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final AccountRepository accountRepository;
    private final TicketStatusRepository ticketStatusRepository;

    private final TicketMapper ticketMapper;

    public TicketService(TicketRepository ticketRepository, TicketMapper ticketMapper,
                         TicketStatusRepository ticketStatusRepository,  AccountRepository accountRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.ticketStatusRepository = ticketStatusRepository;
        this.accountRepository = accountRepository;
    }

    public Ticket save(Ticket ticket) {
        ticketRepository.save(ticket);
        return ticket;
    }

    public Optional<Ticket> getTicketById(Long id) {
        return ticketRepository.findById(id);
    }

    public Ticket assignAgent(Long ticketId, Long agentId) {
        Ticket ticket = ticketRepository.getReferenceById(ticketId);
        accountRepository.findById(agentId).ifPresent(ticket::setAgent);
        ticketRepository.save(ticket);
        return ticket;
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


    public Page<Ticket> findAllPagedWithFilters(Long idAgent, Long idCreator,  Pageable pageable) {
        Specification<Ticket> spec = Specification.where(TicketSpecification.hasIdAgent(idAgent))
                .and(TicketSpecification.hasIdCreator(idCreator));
        return ticketRepository.findAll(spec, pageable);
    }
}
