package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServicoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String codigo;
    private double preco;

    private List<Link> links = new ArrayList<>();

    public void addLink(Link link) {
        this.links.add(link);
    }
}

