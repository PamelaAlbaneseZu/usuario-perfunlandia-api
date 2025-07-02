package com.perfunlandia.usuario.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.perfunlandia.usuario.config.UsuarioMapper;
import com.perfunlandia.usuario.dto.UsuarioDTO;
import com.perfunlandia.usuario.dto.CrearUsuarioRequest;
import com.perfunlandia.usuario.models.Cliente;
import com.perfunlandia.usuario.models.Usuario;
import com.perfunlandia.usuario.models.Vendedor;
import com.perfunlandia.usuario.models.Rol;
import com.perfunlandia.usuario.repository.ClienteRepository;
import com.perfunlandia.usuario.repository.UsuarioRepository;
import com.perfunlandia.usuario.repository.VendedorRepository;
import com.perfunlandia.usuario.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final ClienteRepository clienteRepo;
    private final VendedorRepository vendedorRepo;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder; 
    private final UsuarioMapper mapper;

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepo.findAll().stream()
            .map(mapper::usuarioToDto)
            .toList();
    }

    public Usuario crearUsuario(CrearUsuarioRequest req) {
        Usuario u = new Usuario();
        u.setNombreCompleto(req.getNombreCompleto());
        u.setEmail(req.getEmail());

        String rawPassword = req.getContrasena();
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("La clave es requerida");
        }
        u.setContrasena(passwordEncoder.encode(rawPassword));

        // Buscar rol por nombre
        Rol rol = rolRepository.findByNombreRol(req.getRol().toLowerCase())
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + req.getRol()));
        u.setRol(rol);

        u.setEstado(req.getEstado());
        u.setRut(req.getRut());
        u.setFechaCreacion(LocalDateTime.now().toString());

        // Guarda usuario en tabla 'usuario'
        Usuario nuevoUsuario = usuarioRepo.save(u);

        switch (req.getRol().toLowerCase()) {
            case "cliente" -> {
                Cliente c = new Cliente();
                c.setUsuario(nuevoUsuario);
                c.setIdDireccion(req.getIdDireccion());
                c.setTelefono(req.getTelefono());
                clienteRepo.save(c);
            }
            case "vendedor" -> {
                Vendedor v = new Vendedor();
                v.setUsuario(nuevoUsuario);
                v.setIdSucursal(req.getIdSucursal());
                vendedorRepo.save(v);
            }
            case "admin" -> {
                // No se necesita lógica adicional para admin
            }
            default -> throw new IllegalArgumentException("Rol no soportado: " + req.getRol());
        }

        return nuevoUsuario;
    }

    public UsuarioDTO buscarUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        return mapper.usuarioToDto(usuario);
    }

    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setEstado(dto.getEstado());

        Rol rol = rolRepository.findByNombreRol(dto.getRol().toLowerCase())
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + dto.getRol()));
        usuario.setRol(rol);

        Usuario guardado = usuarioRepo.save(usuario);
        return mapper.usuarioToDto(guardado);
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepo.deleteById(id);
    }
}