package br.com.rd.ProjetoIntegrador.model.entity;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_pedido_key;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Item_pedido {
    @EmbeddedId
    private Item_pedido_key item_pedido_key;
    @Column(nullable = false)
    private Integer quantidade_produto;
    @Column(nullable = false)
    private Double desconto_unitario;
    @Column(nullable = false)
    private Double valor_unitario;
    @Column(nullable = false)
    private Double valor_total;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private Produto produto;
}
