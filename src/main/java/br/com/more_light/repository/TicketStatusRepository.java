package br.com.more_light.repository;

import br.com.more_light.domain.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {
    Optional<TicketStatus> findByCode(String code);
    Optional<TicketStatus> findByName(String name);
}

