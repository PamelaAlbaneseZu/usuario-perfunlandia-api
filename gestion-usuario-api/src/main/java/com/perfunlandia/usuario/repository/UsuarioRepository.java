package com.perfunlandia.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfunlandia.usuario.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}


