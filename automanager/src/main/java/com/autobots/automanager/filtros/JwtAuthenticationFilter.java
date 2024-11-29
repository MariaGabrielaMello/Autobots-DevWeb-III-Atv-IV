package com.autobots.automanager.filtros;

import com.autobots.automanager.dtos.CredencialUsuarioSenhaDTO;
import com.autobots.automanager.jwt.ProvedorJwt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ProvedorJwt provedorJwt;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ProvedorJwt provedorJwt) {
        this.authenticationManager = authenticationManager;
        this.provedorJwt = provedorJwt;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            CredencialUsuarioSenhaDTO credenciais = new ObjectMapper().readValue(request.getInputStream(), CredencialUsuarioSenhaDTO.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credenciais.getNomeUsuario(),
                            credenciais.getSenha(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuário", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String nomeUsuario = ((UserDetails) authResult.getPrincipal()).getUsername();

        String token = provedorJwt.gerarToken(nomeUsuario, null); // Supondo que você não precise do perfil no token

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().write("{\"Authorization\": \"Bearer " + token + "\"}"); // Retorna o token no corpo da resposta
    }
}

