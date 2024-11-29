package com.autobots.automanager.entidades;

import javax.persistence.*;

import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.ArrayList;

@Data
@Entity
public class Endereco {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = true)
	private String estado;
	@Column(nullable = false)
	private String cidade;
	@Column(nullable = true)
	private String bairro;
	@Column(nullable = false)
	private String rua;
	@Column(nullable = false)
	private String numero;
	@Column(nullable = true)
	private String codigoPostal;
	@Column(unique = false, nullable = true)
	private String informacoesAdicionais;
	@Transient
	private ArrayList<Link> links;

	@Column(name = "links")
	@Lob
	private String linksAsString;

	public ArrayList<Link> getLinks() {
		if (links == null && linksAsString != null) {
			links = new ArrayList<>();
		}
		return links;
	}

	public void add(Link link) {
		if (this.links == null) {
			this.links = new ArrayList<>();
		}

		if (!this.links.contains(link)) {
			this.links.add(link);
		}
	}
}