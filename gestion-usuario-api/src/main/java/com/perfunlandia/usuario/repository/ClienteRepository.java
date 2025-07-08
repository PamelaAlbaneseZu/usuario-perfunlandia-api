package com.perfunlandia.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfunlandia.usuario.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
