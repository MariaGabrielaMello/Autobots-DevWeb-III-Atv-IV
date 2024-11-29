package com.autobots.automanager.modelos;

import com.autobots.automanager.entidades.Empresa;

public class EmpresaAtualizador {

    public void atualizar(Empresa empresaExistente, Empresa empresaAtualizacao) {
        empresaExistente.setRazaoSocial(empresaAtualizacao.getRazaoSocial());
        empresaExistente.setCnpj(empresaAtualizacao.getCnpj());
        empresaExistente.setEmail(empresaAtualizacao.getEmail());
        empresaExistente.setCadastro(empresaAtualizacao.getCadastro());
        // Adicione aqui outros campos que você deseja atualizar
    }
}
