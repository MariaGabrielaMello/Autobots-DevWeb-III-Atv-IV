package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.VeiculoControle;
import com.autobots.automanager.dtos.VeiculoDTO;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdicionadorLinkVeiculo implements AdicionadorLink<VeiculoDTO> {

    @Override
    public void adicionarLink(VeiculoDTO veiculoDTO) {
        Link selfLink = linkTo(methodOn(VeiculoControle.class).obterVeiculo(veiculoDTO.getId())).withSelfRel();
        veiculoDTO.add(selfLink);

        Link vendasLink = linkTo(methodOn(VeiculoControle.class).obterVendasDoVeiculo(veiculoDTO.getId())).withRel("vendas");
        veiculoDTO.add(vendasLink);
    }

    @Override
    public void adicionarLink(List<VeiculoDTO> veiculosDTO) {
        for (VeiculoDTO veiculoDTO : veiculosDTO) {
            adicionarLink(veiculoDTO);
        }
    }
}

