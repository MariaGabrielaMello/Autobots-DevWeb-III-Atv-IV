package com.autobots.automanager.entidades;
import javax.persistence.*;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.ArrayList;

@Data
@Entity
public class Telefone {
	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String ddd;
	@Column
	private String numero;

	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;

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