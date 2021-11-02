package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.entity.Pedido;
import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Data
public class Item_pedido_keyDTO {
    private Long item;
    private PedidoDTO pedido;
}
