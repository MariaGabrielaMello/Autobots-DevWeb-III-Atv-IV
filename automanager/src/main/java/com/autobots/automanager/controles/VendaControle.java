package com.autobots.automanager.controles;

import com.autobots.automanager.dtos.VendaDTO;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.AdicionadorLinkVenda;
import com.autobots.automanager.repositorios.VendaRepositorio;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/venda")
@AllArgsConstructor
public class VendaControle {
    private VendaRepositorio repositorio;
    private ModelMapper modelMapper;
    private AdicionadorLinkVenda adicionadorLink;

    @GetMapping("/venda/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<EntityModel<VendaDTO>> obterVenda(@PathVariable long id) {
        Venda venda = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Venda não encontrada"));
        VendaDTO vendaDTO = modelMapper.map(venda, VendaDTO.class);
        adicionadorLink.adicionarLink(vendaDTO);
        return ResponseEntity.ok(EntityModel.of(vendaDTO));
    }

    @GetMapping("/vendas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<List<VendaDTO>> obterVendas() {
        List<Venda> vendas = repositorio.findAll();
        if (vendas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma venda encontrada");
        }
        List<VendaDTO> vendasDTO = vendas.stream()
                .map(venda -> modelMapper.map(venda, VendaDTO.class))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(vendasDTO);
        return ResponseEntity.ok
                (vendasDTO);
    }

    @PostMapping("/cadastro")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda) {
        repositorio.save(venda);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/venda/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> deletarVenda(@PathVariable long id) {
        repositorio.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}