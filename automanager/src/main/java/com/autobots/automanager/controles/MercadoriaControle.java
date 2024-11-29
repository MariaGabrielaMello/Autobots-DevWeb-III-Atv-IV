package com.autobots.automanager.controles;

import com.autobots.automanager.dtos.MercadoriaDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.AdicionadorLinkMercadoria;

import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mercadoria")
@AllArgsConstructor
public class MercadoriaControle {

    private MercadoriaRepositorio repositorio;
    private ModelMapper modelMapper;
    private AdicionadorLinkMercadoria adicionadorLink;

    @GetMapping("/mercadoria/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<EntityModel<MercadoriaDTO>> obterMercadoria(@PathVariable long id) {
        Mercadoria mercadoria = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mercadoria não encontrada"));
        MercadoriaDTO mercadoriaDTO = modelMapper.map(mercadoria, MercadoriaDTO.class);
        adicionadorLink.adicionarLink(mercadoriaDTO);
        return ResponseEntity.ok(EntityModel.of(mercadoriaDTO));
    }

    @GetMapping("/mercadorias")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<List<MercadoriaDTO>> obterMercadorias() {
        List<Mercadoria> mercadorias = repositorio.findAll();
        if (mercadorias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma mercadoria encontrada");
        }
        List<MercadoriaDTO> mercadoriasDTO = mercadorias.stream()
                .map(mercadoria -> modelMapper.map(mercadoria, MercadoriaDTO.class))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(mercadoriasDTO);
        return ResponseEntity.ok(mercadoriasDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> cadastrarMercadoria(@RequestBody MercadoriaDTO mercadoriaDTO) {
        Mercadoria mercadoria = modelMapper.map(mercadoriaDTO, Mercadoria.class);
        if (mercadoria.getCadastro() == null) {
            mercadoria.setCadastro(new Date());
        }
        if (mercadoria.getFabricao() == null) {
            mercadoria.setFabricao(new Date());
        }
        if (mercadoria.getValidade() == null) {
            return new ResponseEntity<>("The 'validade' field cannot be null", HttpStatus.BAD_REQUEST);
        }
        repositorio.save(mercadoria);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PutMapping("/atualizar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria mercadoria) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (mercadoria.getId() != null) {
            repositorio.save(mercadoria);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> excluirMercadoria(@RequestBody Mercadoria mercadoria) {
        if (mercadoria.getId() != null) {
            repositorio.delete(mercadoria);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
