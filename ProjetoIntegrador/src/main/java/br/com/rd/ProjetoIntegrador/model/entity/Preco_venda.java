package br.com.rd.ProjetoIntegrador.model.entity;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.PrecoKey;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Preco_venda {
    @EmbeddedId
    private PrecoKey precoKey;
    @Column(nullable = false)
    private double valor_preco;
}
