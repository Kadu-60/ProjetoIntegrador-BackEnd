package br.com.rd.ProjetoIntegrador.model.entity;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Estoquekey;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Estoque {
    @EmbeddedId
    private Estoquekey estoquekey;
    @Column(nullable = false)
    private Integer quantidade;
}
