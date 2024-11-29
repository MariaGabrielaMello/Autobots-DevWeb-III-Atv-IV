package com.autobots.automanager.controles;

import com.autobots.automanager.dtos.ServicoDTO;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.AdicionadorLinkServico;
import com.autobots.automanager.repositorios.ServicoRepositorio;
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
@RequestMapping("/servico")
@AllArgsConstructor
public class ServicoControle {
    private ServicoRepositorio repositorio;
    private ModelMapper modelMapper;
    private AdicionadorLinkServico adicionadorLink;

    @GetMapping("/servico/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<EntityModel<ServicoDTO>> obterServico(@PathVariable long id) {
        Servico servico = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico não encontrado"));
        ServicoDTO servicoDTO = modelMapper.map(servico, ServicoDTO.class);
        adicionadorLink.adicionarLink(servicoDTO);
        return ResponseEntity.ok(EntityModel.of(servicoDTO));
    }

    @GetMapping("/servicos")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<List<ServicoDTO>> obterServicos() {
        List<Servico> servicos = repositorio.findAll();
        if (servicos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum serviço encontrado");
        }
        List<ServicoDTO> servicosDTO = servicos.stream()
                .map(servico -> modelMapper.map(servico, ServicoDTO.class))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(servicosDTO);
        return ResponseEntity.ok(servicosDTO);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> cadastrarServico(@RequestBody Servico servico) {
        repositorio.save(servico);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PutMapping("/atualizar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> atualizarServico(@RequestBody Servico servico) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (servico.getId() != null) {
            repositorio.save(servico);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> excluirServico(@RequestBody Servico servico) {
        if (servico.getId() != null) {
            repositorio.delete(servico);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
