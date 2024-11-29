package com.autobots.automanager.modelos;

import com.autobots.automanager.enumeracoes.TipoDocumento;

// Arquivo: StringVerificadorNulo.java
public class StringVerificadorNulo {
	public boolean verificar(String valor) {
		return valor == null || valor.isEmpty();
	}

	public boolean verificar(TipoDocumento tipoDocumento) {
		return tipoDocumento == null;
	}
}