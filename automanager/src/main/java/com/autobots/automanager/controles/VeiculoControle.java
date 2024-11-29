package com.autobots.automanager.controles;

import com.autobots.automanager.dtos.VeiculoDTO;
import com.autobots.automanager.dtos.VendaDTO;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.AdicionadorLinkVeiculo;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veiculo")
@AllArgsConstructor
public class VeiculoControle {

    private VeiculoRepositorio repositorio;
    private ModelMapper modelMapper;
    private AdicionadorLinkVeiculo adicionadorLink;
    private UsuarioRepositorio usuarioRepositorio;

    @GetMapping("/veiculo/{id}")
    public ResponseEntity<EntityModel<VeiculoDTO>> obterVeiculo(@PathVariable long id) {
        Veiculo veiculo = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));
        VeiculoDTO veiculoDTO = modelMapper.map(veiculo, VeiculoDTO.class);
        adicionadorLink.adicionarLink(veiculoDTO);
        return ResponseEntity.ok(EntityModel.of(veiculoDTO));
    }

    @GetMapping("/veiculos")
    public ResponseEntity<List<VeiculoDTO>> obterVeiculos() {
        List<Veiculo> veiculos = repositorio.findAll();
        if (veiculos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum veículo encontrado");
        }
        List<VeiculoDTO> veiculosDTO = veiculos.stream()
                .map(veiculo -> modelMapper.map(veiculo, VeiculoDTO.class))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(veiculosDTO);
        return ResponseEntity.ok(veiculosDTO);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarVeiculo(@RequestBody VeiculoDTO veiculoDTO) {
        Veiculo veiculo = modelMapper.map(veiculoDTO, Veiculo.class);
        if (veiculo.getId() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (veiculo.getProprietario() != null) {
            usuarioRepositorio.findById(veiculo.getProprietario().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        }

        repositorio.save(veiculo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarVeiculo(@RequestBody Veiculo veiculo) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (veiculo.getId() != null) {
            repositorio.save(veiculo);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> excluirVeiculo(@RequestBody Veiculo veiculo) {
        if (veiculo.getId() != null) {
            repositorio.delete(veiculo);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/vendas")
    public ResponseEntity<List<Venda>> obterVendasDoVeiculo(@PathVariable Long id) {
        Veiculo veiculo = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo não encontrado"));
        return ResponseEntity.ok(new ArrayList<>(veiculo.getVendas()));
    }
}

