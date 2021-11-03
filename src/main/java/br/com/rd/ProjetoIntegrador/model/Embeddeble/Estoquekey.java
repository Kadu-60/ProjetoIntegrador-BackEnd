package br.com.rd.ProjetoIntegrador.model.Embeddeble;

import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class Estoquekey implements Serializable {
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private Produto produto;
}
