package br.com.more_light.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TicketDTO {
    private Long id;
    private String description;
    private Integer statusId;
    private Date createdAt;
    private AccountDTO createdBy;
}
