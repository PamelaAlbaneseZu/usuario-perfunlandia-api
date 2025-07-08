package com.perfunlandia.usuario.dto;

import com.perfunlandia.usuario.models.Usuario;
import com.perfunlandia.usuario.models.Vendedor;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VendedorDTO extends RepresentationModel<VendedorDTO> {
    private Integer idVendedor; 
    
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    private Integer idSucursal;

    //Este método se utiliza para convertir un objeto Vendedor en un objeto VendedorDTO.
    //El método recibe un objeto Vendedor como parámetro y crea un nuevo objeto VendedorDTO.
    //Luego, se asignan los valores de los atributos de Vendedor a los atributos correspondientes del objeto VendedorDTO.
    //Finalmente, se devuelve el objeto VendedorDTO convertido.
    public static VendedorDTO convertFromEntity(Vendedor vendedor) {
        VendedorDTO dto = new VendedorDTO();
        dto.setIdVendedor(vendedor.getIdVendedor());
        dto.setUsuario(vendedor.getUsuario());
        dto.setIdSucursal(vendedor.getIdSucursal());
        return dto;
    }
}

