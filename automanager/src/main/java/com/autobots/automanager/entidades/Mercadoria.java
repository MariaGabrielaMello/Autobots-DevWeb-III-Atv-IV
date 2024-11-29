package com.autobots.automanager.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"empresa", "vendas"})
@Entity
@Relation(collectionRelation = "mercadorias")
public class Mercadoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column
	private String descricao;

	@Column(nullable = false)
	private String codigo;

	@Column(nullable = false)
	private double valor;

	@Column(nullable = false)
	private int quantidade;

	@Column(nullable = false)
	private Date cadastro;

	@Column(nullable = false)
	private Date fabricao;

	@Column(nullable = false)
	private Date validade;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "empresa_id")
	@JsonBackReference
	private Empresa empresa;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "venda_id")
	private Venda venda;
}
