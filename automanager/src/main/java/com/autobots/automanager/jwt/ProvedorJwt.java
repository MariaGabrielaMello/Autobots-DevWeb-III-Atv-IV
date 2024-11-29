package com.autobots.automanager.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
public class ProvedorJwt {
	@Value("${jwt.secret}")
	private String assinatura;
	@Value("${jwt.expiration}")
	private Long duracao;

	private final GeradorJwt geradorJwt;
	private final AnalisadorJwt analisadorJwt;
	private final ValidadorJwt validadorJwt;

	public ProvedorJwt(GeradorJwt geradorJwt, AnalisadorJwt analisadorJwt, ValidadorJwt validadorJwt) {
		this.geradorJwt = geradorJwt;
		this.analisadorJwt = analisadorJwt;
		this.validadorJwt = validadorJwt;
	}

	public String gerarToken(String nomeUsuario, String perfil) {
		return geradorJwt.gerarJwt(nomeUsuario, perfil);
	}

	public boolean validarJwt(String jwt) {
		Claims reivindicacoes = analisadorJwt.analisarJwt(jwt);
		return validadorJwt.validar(reivindicacoes);
	}

	public String obterNomeUsuario(String jwt) {
		Claims reivindicacoes = analisadorJwt.analisarJwt(jwt);
		return analisadorJwt.obterNomeUsuario(reivindicacoes);
	}
}
