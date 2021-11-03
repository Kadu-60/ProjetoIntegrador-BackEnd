package br.com.rd.ProjetoIntegrador.model.Embeddeble;

import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
@Data
public class PrecoKey implements Serializable {
    @Column(nullable = false)
    private Date data_vigencia;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private Produto produto;

}
