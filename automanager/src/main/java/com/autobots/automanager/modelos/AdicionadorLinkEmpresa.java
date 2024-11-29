package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.MercadoriaControle;
import com.autobots.automanager.dtos.EmpresaDTO;
import com.autobots.automanager.dtos.MercadoriaDTO;
import com.autobots.automanager.entidades.Empresa;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;


import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AdicionadorLinkEmpresa implements AdicionadorLink<Empresa> {

    public void adicionarLink(Empresa empresa) {

        empresa.add(linkTo(methodOn(EmpresaControle.class).obterEmpresa(empresa.getId())).withSelfRel());
        empresa.add(linkTo(methodOn(EmpresaControle.class).obterUsuariosDaEmpresa(empresa.getId())).withRel("usuarios"));
        empresa.add(linkTo(methodOn(EmpresaControle.class).obterMercadoriasDaEmpresa(empresa.getId())).withRel("mercadorias"));
        empresa.add(linkTo(methodOn(EmpresaControle.class).obterServicosDaEmpresa(empresa.getId())).withRel("servicos"));
        empresa.add(linkTo(methodOn(EmpresaControle.class).obterVendasDaEmpresa(empresa.getId())).withRel("vendas"));
    }


    @Override
    public void adicionarLink(List<Empresa> empresas) {
        for (Empresa empresa : empresas) {
            adicionarLink(empresa);
        }
    }

    public void adicionarLink(EmpresaDTO empresaDTO) {
        Link selfLink = linkTo(methodOn(EmpresaControle.class).obterEmpresa(empresaDTO.getId())).withSelfRel();
        empresaDTO.add(selfLink);
    }

    public void adicionarLinks(List<EntityModel<Empresa>> empresas) {
        empresas.forEach(this::adicionarLink);
    }

    public void adicionarLink(EntityModel<Empresa> empresa) {
        Link selfLink = linkTo(methodOn(EmpresaControle.class)
                .obterEmpresa(empresa.getContent().getId())).withSelfRel();
        empresa.add(selfLink);
    }

}







