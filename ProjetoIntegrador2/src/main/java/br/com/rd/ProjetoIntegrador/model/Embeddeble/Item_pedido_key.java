package br.com.rd.ProjetoIntegrador.model.Embeddeble;

import br.com.rd.ProjetoIntegrador.model.entity.Pedido;
import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class Item_pedido_key implements Serializable {
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_item;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
