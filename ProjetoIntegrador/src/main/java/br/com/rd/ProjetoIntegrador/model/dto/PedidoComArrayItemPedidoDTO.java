package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PedidoComArrayItemPedidoDTO {
    PedidoDTO pedido;
    List<Item_pedidoDTO> arrayItens;

}
