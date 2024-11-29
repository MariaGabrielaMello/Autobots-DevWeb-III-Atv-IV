package com.autobots.automanager.controles;

import com.autobots.automanager.dtos.CredencialUsuarioSenhaDTO;
import com.autobots.automanager.dtos.UsuarioDTO;
import com.autobots.automanager.dtos.VeiculoDTO;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.modelos.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.AdicionadorLinkVeiculo;
import com.autobots.automanager.repositorios.CredencialRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioControle {
    @Autowired
    private AdicionadorLinkVeiculo adicionadorLinkVeiculo;
    private UsuarioRepositorio repositorio;
    private ModelMapper modelMapper;
    private AdicionadorLinkUsuario adicionadorLink;
    private UsuarioRepositorio usuarioRepositorio;
    private CredencialRepositorio credencialRepositorio;

    @GetMapping("/usuario/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<EntityModel<UsuarioDTO>> obterUsuario(@PathVariable long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);
        adicionadorLink.adicionarLink(usuarioDTO);
        return ResponseEntity.ok(EntityModel.of(usuarioDTO));
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<List<UsuarioDTO>> obterUsuarios() {
        List<Usuario> usuarios = repositorio.findAll();
        if (usuarios.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum usuário encontrado");
        }
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
        adicionadorLink.adicionarLink(usuariosDTO);
        return ResponseEntity.ok(usuariosDTO);
    }

    @PostMapping("/cadastro")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuario usuario) {
        repositorio.save(usuario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> atualizarUsuario(@RequestBody Usuario usuario) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (usuario.getId() != null) {
            repositorio.save(usuario);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GERENTE')")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long id) {
        if (!usuarioRepositorio.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{id}/veiculos")
    public ResponseEntity<CollectionModel<EntityModel<VeiculoDTO>>> obterVeiculosDoUsuario(@PathVariable Long id) {
        Usuario usuario = repositorio.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        List<EntityModel<VeiculoDTO>> veiculosDTO = usuario.getVeiculos().stream()
                .map(veiculo -> {
                    VeiculoDTO dto = modelMapper.map(veiculo, VeiculoDTO.class);
                    adicionadorLinkVeiculo.adicionarLink(dto);
                    return EntityModel.of(dto); // Embrulha o DTO em EntityModel
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(veiculosDTO,
                linkTo(methodOn(UsuarioControle.class).obterUsuarios()).withRel("usuarios")));
    }

    @PostMapping("/adicionarCredencial/{idUsuario}")
    public ResponseEntity<?> adicionarCredencial(@PathVariable Long idUsuario, @RequestBody CredencialUsuarioSenhaDTO credencialDTO) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com id: " + idUsuario));

        CredencialUsuarioSenha novaCredencial = modelMapper.map(credencialDTO, CredencialUsuarioSenha.class);
        novaCredencial.setUsuario(usuario);
        credencialRepositorio.save(novaCredencial);
        usuario.getCredenciais().add(novaCredencial);
        usuario.setCredencial(novaCredencial);
        usuarioRepositorio.save(usuario);

        return ResponseEntity.ok().body("Credencial adicionada com sucesso!");
    }
}
