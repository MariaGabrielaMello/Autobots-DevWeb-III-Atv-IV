package com.autobots.automanager.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class AnalisadorJwt {

	@Value("${jwt.secret}")
	private String assinatura;

	public AnalisadorJwt() {
	}

	public Claims obterReivindicacoes(String jwt) {
		try {
			return Jwts.parser().setSigningKey(assinatura.getBytes()).parseClaimsJws(jwt).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public Claims analisarJwt(String jwt) {
		return Jwts.parser()
				.setSigningKey(assinatura)
				.parseClaimsJws(jwt)
				.getBody();
	}

	public String obterNomeUsuario(Claims reivindicacoes) {
		return reivindicacoes.get("nomeUsuario", String.class);
	}
}
