package com.autobots.automanager.dtos;

import com.autobots.automanager.enumeracoes.TipoVeiculo;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class VeiculoDTO extends RepresentationModel<VeiculoDTO> {
    private Long id;
    private TipoVeiculo tipo;
    private String modelo;
    private String placa;
}

