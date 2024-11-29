package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.VendaControle;
import com.autobots.automanager.dtos.VendaDTO;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdicionadorLinkVenda implements AdicionadorLink<VendaDTO> {

    @Override
    public void adicionarLink(VendaDTO vendaDTO) {
        Link selfLink = linkTo(methodOn(VendaControle.class).obterVenda(vendaDTO.getId())).withSelfRel();
        vendaDTO.addLink(selfLink);
    }

    @Override
    public void adicionarLink(List<VendaDTO> vendaDTOs) {
        for (VendaDTO dto : vendaDTOs) {
            adicionarLink(dto);
        }
    }
}
