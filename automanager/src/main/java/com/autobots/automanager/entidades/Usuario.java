package com.autobots.automanager.entidades;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


import com.autobots.automanager.enumeracoes.PerfilUsuario;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "mercadorias", "vendas", "veiculos" })
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column
	private String nomeSocial;
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<PerfilUsuario> perfis = new HashSet<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Telefone> telefones = new HashSet<>();
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Documento> documentos = new HashSet<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Email> emails = new HashSet<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Credencial> credenciais = new HashSet<>();
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	private Set<Mercadoria> mercadorias = new HashSet<>();
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Venda> vendas = new HashSet<>();
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
	private Set<Veiculo> veiculos = new HashSet<>();
	@OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
	private CredencialUsuarioSenha credencial; // Relacionamento com CredencialUsuarioSenha
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "USUARIO_PERMISSAO",
			joinColumns = @JoinColumn(name = "USUARIO_ID"),
			inverseJoinColumns = @JoinColumn(name = "PERMISSAO_ID")
	)
	private Set<Permissao> permissoes = new HashSet<>();

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, nomeSocial, perfis, telefones, endereco, documentos, emails, mercadorias, vendas, veiculos, empresa);
	}
}