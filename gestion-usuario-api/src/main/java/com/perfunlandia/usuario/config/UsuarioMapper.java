package com.perfunlandia.usuario.config;

import com.perfunlandia.usuario.models.Usuario;
import com.perfunlandia.usuario.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDTO usuarioToDto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setEmail(usuario.getEmail());
        dto.setEstado(usuario.getEstado());
        dto.setRut(usuario.getRut());
        dto.setFechaCreacion(usuario.getFechaCreacion().toString());
        dto.setRol(usuario.getRol() != null ? usuario.getRol().getNombreRol() : null);
        return dto;
    }

    public Usuario dtoToUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(dto.getIdUsuario());
        usuario.setNombreCompleto(dto.getNombreCompleto());
        usuario.setEmail(dto.getEmail());
        usuario.setEstado(dto.getEstado());
        usuario.setRut(dto.getRut());
        usuario.setFechaCreacion(dto.getFechaCreacion());

        // Rol se setea desde UsuarioService
        return usuario;
    }
}