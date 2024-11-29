package com.autobots.automanager.dtos;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.Date;
import java.util.List;

@Data
public class EmpresaDTO {
    private Long id;
    private String razaoSocial;
    private String cnpj;
    private String email;
    private Date cadastro;

    public void add(Link selfLink) {
    }
}

