package com.autobots.automanager.entidades;

import javax.persistence.*;

@Entity
@Table(name = "USUARIO_PERMISSAO")
public class UsuarioPermissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "PERMISSAO_ID")
    private Permissao permissao;

    public UsuarioPermissao() {}

    public UsuarioPermissao(Usuario usuario, Permissao permissao) {
        this.usuario = usuario;
        this.permissao = permissao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }
}