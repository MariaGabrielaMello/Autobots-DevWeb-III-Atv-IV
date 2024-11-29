package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.dtos.MercadoriaDTO;
import com.autobots.automanager.modelos.AdicionadorLink;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdicionadorLinkMercadoria implements AdicionadorLink<MercadoriaDTO> {

    @Override
    public void adicionarLink(MercadoriaDTO mercadoriaDTO) {
        Link selfLink = linkTo(methodOn(MercadoriaControle.class).obterMercadoria(mercadoriaDTO.getId())).withSelfRel();
        mercadoriaDTO.addLink(selfLink);

    }

    @Override
    public void adicionarLink(List<MercadoriaDTO> mercadoriaDTO) {
        for (MercadoriaDTO dto : mercadoriaDTO) {
            adicionarLink(dto);
        }
    }
}
