package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.Date;

@Data
public class MercadoriaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String codigo;
    private double preco;
    private int quantidadeEstoque;
    private Date cadastro;
    private Date fabricao;
    private Date validade;

    public void addLink(Link selfLink) {

    }
}
