package com.perfunlandia.usuario.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Entity
@Table(name = "vendedor_detalle")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Vendedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_vendedor")
    private Integer idVendedor;

    @Column(name = "id_sucursal")
    private Integer idSucursal;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

}