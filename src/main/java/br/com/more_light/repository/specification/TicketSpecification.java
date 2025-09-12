package br.com.more_light.repository.specification;

import br.com.more_light.domain.Ticket;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecification {

    public static Specification<Ticket> hasIdAgent(Long id) {
        return (root, query, criteriaBuilder) ->
                id == null ? null : criteriaBuilder.equal(root.get("agent"), id);
    }

    public static Specification<Ticket> hasIdCreator(Long id) {
        return (root, query, criteriaBuilder) ->
                id == null ? null : criteriaBuilder.equal(root.get("createdBy"), id);
    }
}
