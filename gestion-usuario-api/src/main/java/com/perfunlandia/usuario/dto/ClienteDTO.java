package com.perfunlandia.usuario.dto;

import com.perfunlandia.usuario.models.Cliente;
import com.perfunlandia.usuario.models.Usuario;

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
public class ClienteDTO extends RepresentationModel<ClienteDTO> {
    private Integer idCliente;
    private Integer idDireccion;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    private String telefono;

    //Este método se utiliza para convertir un objeto Cliente en un objeto ClienteDTO.
    //recibe un objeto Cliente como parámetro y crea un nuevo objeto ClienteDTO.
    //Luego, se asignan los valores de los atributos de Cliente a los atributos correspondientes del objeto ClienteDTO.
    //Finalmente, se devuelve el objeto ClienteDTO convertido.
    public static ClienteDTO convertFromEntity(Cliente cliente) {
    ClienteDTO dto = new ClienteDTO();
    dto.setIdCliente(cliente.getIdCliente());
    dto.setUsuario(cliente.getUsuario());
    dto.setIdDireccion(cliente.getIdDireccion());
    dto.setTelefono(cliente.getTelefono());
    return dto;
}
}


