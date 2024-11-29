package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.ServicoControle;
import com.autobots.automanager.dtos.ServicoDTO;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdicionadorLinkServico implements AdicionadorLink<ServicoDTO> {

    @Override
    public void adicionarLink(ServicoDTO servicoDTO) {
        Link selfLink = linkTo(methodOn(ServicoControle.class).obterServico(servicoDTO.getId())).withSelfRel();
        servicoDTO.addLink(selfLink);
    }

    @Override
    public void adicionarLink(List<ServicoDTO> servicoDTOs) {
        for (ServicoDTO dto : servicoDTOs) {
            adicionarLink(dto);
        }
    }
}