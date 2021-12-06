package br.com.rd.ProjetoIntegrador.model.entity;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.ClienteCartaoKey;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
public class ClienteCartao {
    @EmbeddedId
    ClienteCartaoKey clienteCartaoKey;
    @Column(nullable = false)
    Boolean principal;
}
