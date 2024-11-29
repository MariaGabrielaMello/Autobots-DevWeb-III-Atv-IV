package com.autobots.automanager.dtos;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class ServicoPrestadoDTO {
    private Long id;
    private Date data;
    private double valor;
    private String descricao;
    private List<Long> servicoIds;
}

