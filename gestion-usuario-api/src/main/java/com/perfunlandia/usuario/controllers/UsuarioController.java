package com.perfunlandia.usuario.controllers;

import com.perfunlandia.usuario.dto.CrearUsuarioRequest;
import com.perfunlandia.usuario.dto.UsuarioDTO;
import com.perfunlandia.usuario.models.Usuario;
import com.perfunlandia.usuario.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Gestión de Usuarios", description = "API para la gestión de usuarios (clientes y vendedores)")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Listar todos los usuarios", description = "Obtiene la lista completa de usuarios registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @Operation(summary = "Buscar usuario por ID", description = "Obtiene un usuario específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@Parameter(description = "ID del usuario a buscar") @PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(id));
    }

    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario (cliente o vendedor)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<Usuario> crear(@Parameter(description = "Datos del usuario a crear") @RequestBody CrearUsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.crearUsuario(request));
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UsuarioDTO.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@Parameter(description = "ID del usuario a actualizar") @PathVariable Integer id, 
                                               @Parameter(description = "Datos actualizados del usuario") @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del usuario a eliminar") @PathVariable Integer id) {
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