package br.com.rd.ProjetoIntegrador.model.entity;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_Nf_Key;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Item_Nf {
    @EmbeddedId
    private Item_Nf_Key item_nf_key;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private Produto produto;
    @Column(nullable = false)
    private Integer quantidade_produto;
    @Column(nullable = false)
    private Double desconto_produto;
    @Column(nullable = false)
    private Double icms;
    @Column(nullable = false)
    private Double cofins;
    @Column(nullable = false)
    private Double valor_unitario;
    @Column(nullable = false)
    private Double valor_total;

}
