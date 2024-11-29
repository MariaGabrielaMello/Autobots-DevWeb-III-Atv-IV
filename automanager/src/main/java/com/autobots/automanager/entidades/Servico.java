package com.autobots.automanager.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.server.core.Relation;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"empresa", "servicosPrestados"})
@Entity
@Relation(collectionRelation = "servicos")
public class Servico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nome;

	@Column
	private String descricao;

	@Column(nullable = false)
	private double valor;
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	@JsonBackReference
	private Empresa empresa;

	@ManyToMany(mappedBy = "servicos")
	private Set<ServicoPrestado> servicosPrestados = new HashSet<>();
}

