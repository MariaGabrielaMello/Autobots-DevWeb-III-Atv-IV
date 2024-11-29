package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class VendaDTO {
    private Long id;
    private Date cadastro;
    private String identificacao;
    private Long clienteId;
    private Long funcionarioId;
    private Long veiculoId;
    private double valorTotal;
    private List<Long> mercadoriaIds;
    private List<Long> servicoIds;

    private List<Link> links = new ArrayList<>();

    public void addLink(Link link) {
        this.links.add(link);
    }
}

