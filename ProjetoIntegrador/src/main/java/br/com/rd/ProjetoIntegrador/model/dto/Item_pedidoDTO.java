package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_pedido_key;
import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import lombok.Data;

@Data
public class Item_pedidoDTO {
    private Item_pedido_keyDTO item_pedido_key;
    private ProdutoDTO produto;
    private Integer quantidade_produto;
    private Double desconto_unitario;
    private Double valor_unitario;
    private Double valor_total;
}
