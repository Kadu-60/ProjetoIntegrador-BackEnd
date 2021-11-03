package br.com.rd.ProjetoIntegrador.model.Embeddeble;

import br.com.rd.ProjetoIntegrador.model.entity.Nf;
import br.com.rd.ProjetoIntegrador.model.entity.Produto;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class Item_Nf_Key implements Serializable {
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_item;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_nf")
    private Nf nf;
}
