package com.perfunlandia.usuario.controllers;

import com.perfunlandia.usuario.dto.CrearUsuarioRequest;
import com.perfunlandia.usuario.dto.UsuarioDTO;
import com.perfunlandia.usuario.models.Usuario;
import com.perfunlandia.usuario.services.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    //  Crear usuario nuevo (cliente o vendedor)
    @PostMapping
    public ResponseEntity<Usuario> crear(@RequestBody CrearUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.crearUsuario(request));
    }

    //  Actualizar datos del usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    //  Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // ===== MÉTODOS CON HATEOAS =====

    // Listar todos los usuarios con HATEOAS
    @GetMapping("/hateoas")
    public ResponseEntity<CollectionModel<EntityModel<UsuarioDTO>>> listarConHateoas() {
        List<EntityModel<UsuarioDTO>> usuarios = usuarioService.listarUsuarios().stream()
            .map(usuario -> {
                EntityModel<UsuarioDTO> entityModel = EntityModel.of(usuario);
                // Enlaces HATEOAS al API Gateway
                entityModel.add(Link.of("http://localhost:8888/api/usuarios/" + usuario.getIdUsuario(), "self"));
                entityModel.add(Link.of("http://localhost:8888/api/usuarios/" + usuario.getIdUsuario(), "actualizar"));
                entityModel.add(Link.of("http://localhost:8888/api/usuarios/" + usuario.getIdUsuario(), "eliminar"));
                return entityModel;
            })
            .collect(Collectors.toList());

        CollectionModel<EntityModel<UsuarioDTO>> collectionModel = CollectionModel.of(usuarios);
        collectionModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas", "self"));
        collectionModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas", "crear"));

        return ResponseEntity.ok(collectionModel);
    }

    // Buscar por ID con HATEOAS
    @GetMapping("/hateoas/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> obtenerConHateoas(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.buscarUsuarioPorId(id);
        
        EntityModel<UsuarioDTO> entityModel = EntityModel.of(usuario);
        // Enlaces HATEOAS al API Gateway
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas/" + id, "self"));
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas/" + id, "actualizar"));
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas/" + id, "eliminar"));
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas", "usuarios"));
        
        return ResponseEntity.ok(entityModel);
    }

    // Crear usuario nuevo con HATEOAS
    @PostMapping("/hateoas")
    public ResponseEntity<EntityModel<Usuario>> crearConHateoas(@RequestBody CrearUsuarioRequest request) {
        Usuario usuario = usuarioService.crearUsuario(request);
        
        EntityModel<Usuario> entityModel = EntityModel.of(usuario);
        // Enlaces HATEOAS al API Gateway
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas/" + usuario.getIdUsuario(), "ver"));
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas", "usuarios"));
        
        return ResponseEntity.ok(entityModel);
    }

    // Actualizar datos del usuario con HATEOAS
    @PutMapping("/hateoas/{id}")
    public ResponseEntity<EntityModel<UsuarioDTO>> actualizarConHateoas(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        UsuarioDTO usuario = usuarioService.actualizarUsuario(id, dto);
        
        EntityModel<UsuarioDTO> entityModel = EntityModel.of(usuario);
        // Enlaces HATEOAS al API Gateway
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas/" + id, "self"));
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas/" + id, "eliminar"));
        entityModel.add(Link.of("http://localhost:8888/api/usuarios/hateoas", "usuarios"));
        
        return ResponseEntity.ok(entityModel);
    }

    // Eliminar usuario con HATEOAS
    @DeleteMapping("/hateoas/{id}")
    public ResponseEntity<Void> eliminarConHateoas(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}