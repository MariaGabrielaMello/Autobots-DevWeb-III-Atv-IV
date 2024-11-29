package com.autobots.automanager.dtos;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Set;

@Data
public class UsuarioDTO extends RepresentationModel<UsuarioDTO>{
    private Long id;
    private String nome;
    private String nomeSocial;
    private Set<PerfilUsuario> perfis;
    private String contato;
}
