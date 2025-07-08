package com.perfunlandia.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfunlandia.usuario.models.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario> findByEmail(String email);
}


