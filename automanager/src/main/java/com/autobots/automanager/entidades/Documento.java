package com.autobots.automanager.entidades;

import java.util.Date;

import javax.persistence.*;

import com.autobots.automanager.enumeracoes.TipoDocumento;


import lombok.Data;
import org.springframework.hateoas.Link;

@Data
@Entity
public class Documento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private TipoDocumento tipo;
	@Column(nullable = false)
	private Date dataEmissao;
	@Column(unique = true, nullable = false)
	private String numero;
	public void addLink(Link link) {
		this.addLink(link);
	}
}