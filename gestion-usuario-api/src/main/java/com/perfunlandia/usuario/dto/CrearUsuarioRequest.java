package com.perfunlandia.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CrearUsuarioRequest {

    //Datos de Usuario
    private String nombreUsuario;
    private String email;
    @JsonProperty("contrasena")
    private String contrasena;
    private String rol;
    private String estado;
    private String fechaCreacion;
    

    //Datos si es Cliente
    private String nombreCompleto;
    private String rut;
    private Integer idDireccion;
    private String telefono;

    //Datos si es Vendedor
    private Integer idSucursal;
}

