package com.autobots.automanager.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class GeradorJwt {
	@Value("${jwt.secret}")
	private String assinatura;

	@Value("${jwt.expiration}")
	private Long duracao;

	private Date expiracao;

	public String gerarJwt(String nomeUsuario, String perfil) {
		Date agora = new Date();
		this.expiracao = new Date(agora.getTime() + duracao);

		Map<String, Object> claims = new HashMap<>();
		claims.put("nomeUsuario", nomeUsuario);
		claims.put("perfil", perfil);

		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(agora)
				.setExpiration(expiracao)
				.signWith(SignatureAlgorithm.HS512, assinatura)
				.compact();
	}
}