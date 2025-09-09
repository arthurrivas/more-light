package br.com.more_light.mapper;

import br.com.more_light.domain.Ticket;
import br.com.more_light.dto.TicketDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status.id", source = "statusId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "createdBy", source = "createdBy")
    Ticket fromDto(TicketDTO ticketDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "statusId", source = "status.id")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "createdBy", source = "createdBy")
    TicketDTO toDTO(Ticket ticket);
}

