package com.autobots.automanager.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente extends RepresentationModel<Cliente>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String nome;
	@Column
	private String nomeSocial;
	@Column
	private Date dataNascimento;
	@Column
	private Date dataCadastro;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Documento> documentos = new ArrayList<>();
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Endereco endereco;
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Telefone> telefones = new ArrayList<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Email> emails = new ArrayList<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Mercadoria> mercadorias = new ArrayList<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Servico> servicos = new ArrayList<>();
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)



	public void addLink(Link link) {
		this.addLink(link);
	}

}