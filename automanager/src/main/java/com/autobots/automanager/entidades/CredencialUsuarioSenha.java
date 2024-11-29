package com.autobots.automanager.entidades;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CredencialUsuarioSenha extends Credencial {
	@Column(nullable = false, unique = true)
	private String nomeUsuario;
	@Column(nullable = false)
	private String senha;

	@OneToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}
}