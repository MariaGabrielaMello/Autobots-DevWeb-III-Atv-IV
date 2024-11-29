package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.modelos.TelefoneSelecionador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/telefone")

public class TelefoneControle {
    @Autowired
    private TelefoneRepositorio repositorio;
    @Autowired
    private TelefoneSelecionador selecionador;
    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;


    @GetMapping("/telefone/{id}")
    public ResponseEntity<Telefone> obterTelefone(@PathVariable long id) {
        List<Telefone> telefones = repositorio.findAll();
        Telefone[] telefonesArray = telefones.toArray(new Telefone[0]);
        Telefone telefone = selecionador.selecionar(telefonesArray, id);
        if (telefone == null) {
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(telefone, HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(telefone);
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(telefone, HttpStatus.FOUND);
            return resposta;
        }
    }

    @GetMapping("/telefones")
    public ResponseEntity <List<Telefone>> obterTelefones() {
        List<Telefone> telefones = repositorio.findAll();
        if (telefones.isEmpty()) {
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(telefones);
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
            return resposta;
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarTelefone(@RequestBody Telefone telefone){
        HttpStatus status = HttpStatus.CONFLICT;
        if (telefone.getId() == null) {
            repositorio.save(telefone);
            status = HttpStatus.CREATED;
        }
        ResponseEntity<?> resposta = new ResponseEntity<>(status);
        return resposta;
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarTelefone(@PathVariable Long id, @RequestBody Telefone atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        List<Telefone> telefones = repositorio.findAll();
        Telefone[] telefonesArray = telefones.toArray(new Telefone[0]);
        Telefone telefone = selecionador.selecionar(telefonesArray, id);
        if (telefone != null) {
            TelefoneAtualizador atualizador = new TelefoneAtualizador();
            atualizador.atualizar(telefone, atualizacao);
            repositorio.save(telefone);
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirTelefone(@PathVariable Long id) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Optional<Telefone> telefone = repositorio.findById(id);
        if (telefone.isPresent()) {
            repositorio.delete(telefone.get());
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

}
