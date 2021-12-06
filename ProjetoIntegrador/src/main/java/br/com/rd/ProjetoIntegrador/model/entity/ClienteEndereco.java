package br.com.rd.ProjetoIntegrador.model.entity;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.ClienteEnderecoKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClienteEndereco {
    @EmbeddedId
    ClienteEnderecoKey clienteEnderecoKey;
    @Column(nullable = false)
    Boolean enderecoPrincipal;
    @Column(nullable = false)
    Boolean enderecoEntrega;
}
