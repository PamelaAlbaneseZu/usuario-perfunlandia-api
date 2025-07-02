package com.perfunlandia.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perfunlandia.usuario.models.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {

}
