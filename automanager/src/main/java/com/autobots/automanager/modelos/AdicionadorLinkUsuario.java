package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.dtos.UsuarioDTO;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<UsuarioDTO> {

    @Override
    public void adicionarLink(UsuarioDTO usuarioDTO) {
        Link selfLink = linkTo(methodOn(UsuarioControle.class).obterUsuario(usuarioDTO.getId())).withSelfRel();
        usuarioDTO.add(selfLink);

        Link veiculosLink = linkTo(methodOn(UsuarioControle.class).obterVeiculosDoUsuario(usuarioDTO.getId())).withRel("veiculos");
        usuarioDTO.add(veiculosLink);
    }

    @Override
    public void adicionarLink(List<UsuarioDTO> usuariosDTO) {
        for (UsuarioDTO usuarioDTO : usuariosDTO) {
            adicionarLink(usuarioDTO);
        }
    }
}
