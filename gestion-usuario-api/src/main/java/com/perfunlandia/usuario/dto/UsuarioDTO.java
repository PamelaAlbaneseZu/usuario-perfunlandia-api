package com.perfunlandia.usuario.dto;

import com.perfunlandia.usuario.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    private Integer idUsuario;
    private String nombreCompleto;
    private String email;
    private String fechaCreacion;
    private String contrasena;
    private String estado;
    private String rut;
    private String rol; // ← usamos el ID del rol

    // Convierte una entidad Usuario a un DTO
    public static UsuarioDTO convertFromEntity(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setEmail(usuario.getEmail());
        dto.setFechaCreacion(usuario.getFechaCreacion().toString());
        dto.setEstado(usuario.getEstado());
        dto.setRut(usuario.getRut());
        dto.setContrasena(usuario.getContrasena());

        // Extraer el ID del rol desde la entidad Rol
        if (usuario.getRol() != null) {
            dto.setRol(usuario.getRol().getNombreRol());
        }

        return dto;
    }
}