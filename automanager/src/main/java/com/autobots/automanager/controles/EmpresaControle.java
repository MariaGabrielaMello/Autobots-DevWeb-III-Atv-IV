package com.autobots.automanager.controles;

import com.autobots.automanager.dtos.EmpresaDTO;
import com.autobots.automanager.dtos.UsuarioDTO;
import com.autobots.automanager.entidades.*;
import com.autobots.automanager.modelos.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelos.EmpresaAtualizador;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/empresa")
public class EmpresaControle {
    @Autowired
    private ModelMapper modelMapper;

    private final EmpresaRepositorio empresaRepositorio;
    private final AdicionadorLinkEmpresa adicionadorLinkEmpresa;
    private UsuarioRepositorio usuarioRepositorio;


    @GetMapping("/empresa/{id}")
    public ResponseEntity<EntityModel<EmpresaDTO>> obterEmpresa(@PathVariable Long id) {
        return empresaRepositorio.findById(id)
                .map(empresa -> {
                    EmpresaDTO empresaDTO = modelMapper.map(empresa, EmpresaDTO.class);
                    adicionadorLinkEmpresa.adicionarLink(empresaDTO);
                    return ResponseEntity.ok(EntityModel.of(empresaDTO));
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/empresas")
    public ResponseEntity<CollectionModel<EntityModel<Empresa>>> obterEmpresas() {
        List<Empresa> empresas = empresaRepositorio.findAll();
        if (empresas.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Empresa>> empresasComLinks = empresas.stream()
                    .map(EntityModel::of) // Cria EntityModel para cada empresa
                    .collect(Collectors.toList());
            adicionarLinks(empresasComLinks); // Chama o método correto
            return ResponseEntity.ok(CollectionModel.of(empresasComLinks));
        }
    }


    @PostMapping("/cadastrar")
    public ResponseEntity<Empresa> cadastrar(@RequestBody EmpresaDTO empresaDTO) {
        Empresa empresa = modelMapper.map(empresaDTO, Empresa.class);
        if (empresa.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        empresaRepositorio.save(empresa);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Empresa empresa = empresaRepositorio.getById(id);
        if (empresa != null) {
            EmpresaAtualizador atualizador = new EmpresaAtualizador();
            atualizador.atualizar(empresa, atualizacao);
            empresaRepositorio.save(empresa);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("excluir/{id}")
    public ResponseEntity<?> excluirEmpresa(@PathVariable Long id) {
        if (!empresaRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        empresaRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/usuarios/{id}")
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> obterUsuariosDaEmpresa(@PathVariable Long id) {
        return empresaRepositorio.findById(id)
                .map(empresa -> {
                    List<UsuarioDTO> usuariosDTO = empresa.getUsuarios().stream()
                            .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                            .collect(Collectors.toList());

                    List<EntityModel<UsuarioDTO>> usuariosComLinks = usuariosDTO.stream()
                            .map(usuarioDTO -> EntityModel.of(usuarioDTO)
                                    .add(WebMvcLinkBuilder.linkTo(
                                            WebMvcLinkBuilder.methodOn(UsuarioControle.class)
                                                    .obterUsuario(usuarioDTO.getId())
                                    ).withSelfRel())
                            )
                            .collect(Collectors.toList());

                    return ResponseEntity.ok(CollectionModel.of(usuariosComLinks));
                })
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/mercadorias/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Mercadoria>>> obterMercadoriasDaEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com id " + id));

        List<Mercadoria> mercadorias = new ArrayList<>(empresa.getMercadorias());
        if (mercadorias.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Mercadoria>> mercadoriasComLinks = new ArrayList<>();
            for (Mercadoria mercadoria : mercadorias) {
                mercadoriasComLinks.add(EntityModel.of(mercadoria));
            }
            return ResponseEntity.ok(CollectionModel.of(mercadoriasComLinks));
        }
    }

    @GetMapping("/servicos/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Servico>>> obterServicosDaEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com id " + id));

        List<Servico> servicos = new ArrayList<>(empresa.getServicos());
        if (servicos.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Servico>> servicosComLinks = new ArrayList<>();
            for (Servico servico : servicos) {
                servicosComLinks.add(EntityModel.of(servico));
            }
            return ResponseEntity.ok(CollectionModel.of(servicosComLinks));
        }
    }


    @GetMapping("/vendas/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Venda>>>obterVendasDaEmpresa(@PathVariable Long id) {
        Empresa empresa = empresaRepositorio.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com id " + id));

        List<Venda> vendas = new ArrayList<>(empresa.getVendas());
        if (vendas.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<EntityModel<Venda>> vendasComLinks = new ArrayList<>();
            for (Venda venda : vendas) {
                vendasComLinks.add(EntityModel.of(venda));
            }
            return ResponseEntity.ok(CollectionModel.of(vendasComLinks));
        }
    }

    @PostMapping("/associar-usuario/{idEmpresa}/{idUsuario}")
    public ResponseEntity<?> associarUsuarioEmpresa(@PathVariable Long idEmpresa, @PathVariable Long idUsuario, @RequestBody Boolean associar) {
        if (associar == null) {
            return ResponseEntity.badRequest().body("Valor de associar é obrigatório");
        }

        Empresa empresa = empresaRepositorio.findById(idEmpresa)
                .orElseThrow(() -> new NoSuchElementException("Empresa não encontrada com id: " + idEmpresa));

        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com id: " + idUsuario));

        usuario.setEmpresa(empresa); // Associa o usuário à empresa

        usuarioRepositorio.save(usuario); // Salva as alterações no banco de dados

        return ResponseEntity.ok().body("Usuário associado à empresa com sucesso!");
    }


    private void adicionarLinks(List<EntityModel<Empresa>> empresas) {
        for (EntityModel<Empresa> empresa : empresas) {
            long id = empresa.getContent().getId();
            Link selfLink = WebMvcLinkBuilder.linkTo(EmpresaControle.class)
                    .slash(id)
                    .withSelfRel();
            empresa.add(selfLink);
        }
    }
}




