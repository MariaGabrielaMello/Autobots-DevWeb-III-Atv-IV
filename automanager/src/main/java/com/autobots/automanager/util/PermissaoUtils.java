package com.autobots.automanager.util;

import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.enumeracoes.PermissaoEnum;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;


public class PermissaoUtils {
    public static Map<PerfilUsuario, Set<PermissaoEnum>> permissoesPorPerfil() {
        Map<PerfilUsuario, Set<PermissaoEnum>> permissoesPorPerfil = new HashMap<>();
        permissoesPorPerfil.put(PerfilUsuario.ADMINISTRADOR, Set.of(
                PermissaoEnum.ADMIN_CRUD,
                PermissaoEnum.GERENTE_CRUD_USUARIO,
                PermissaoEnum.GERENTE_CRUD_SERVICO,
                PermissaoEnum.VENDEDOR_CRUD_CLIENTE,
                PermissaoEnum.VENDEDOR_READ_SERVICO,
                PermissaoEnum.VENDEDOR_READ_MERCADORIA,
                PermissaoEnum.VENDEDOR_CREATE_VENDA,
                PermissaoEnum.VENDEDOR_READ_VENDA,
                PermissaoEnum.CLIENTE_READ_PROPRIO,
                PermissaoEnum.CLIENTE_READ_VENDA
        ));

        permissoesPorPerfil.put(PerfilUsuario.GERENTE, Set.of(
                PermissaoEnum.GERENTE_CRUD_USUARIO,
                PermissaoEnum.GERENTE_CRUD_SERVICO,
                PermissaoEnum.VENDEDOR_CRUD_CLIENTE,
                PermissaoEnum.VENDEDOR_READ_SERVICO,
                PermissaoEnum.VENDEDOR_READ_MERCADORIA,
                PermissaoEnum.VENDEDOR_CREATE_VENDA,
                PermissaoEnum.VENDEDOR_READ_VENDA
        ));

        permissoesPorPerfil.put(PerfilUsuario.VENDEDOR, Set.of(
                PermissaoEnum.VENDEDOR_CRUD_CLIENTE,
                PermissaoEnum.VENDEDOR_READ_SERVICO,
                PermissaoEnum.VENDEDOR_READ_MERCADORIA,
                PermissaoEnum.VENDEDOR_CREATE_VENDA,
                PermissaoEnum.VENDEDOR_READ_VENDA
        ));

        permissoesPorPerfil.put(PerfilUsuario.CLIENTE, Set.of(
                PermissaoEnum.CLIENTE_READ_PROPRIO,
                PermissaoEnum.CLIENTE_READ_VENDA
        ));
        return permissoesPorPerfil;
    }
}
