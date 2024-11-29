package com.autobots.automanager.dtos;

import lombok.Data;
import java.util.Date;

@Data
public class CredencialUsuarioSenhaDTO {
    private Boolean inativo;
    private String nomeUsuario;
    private String senha;
    private Date criacao;
    private Date ultimoAcesso;
}
