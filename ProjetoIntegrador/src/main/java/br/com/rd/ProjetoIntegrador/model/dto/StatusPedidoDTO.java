package br.com.rd.ProjetoIntegrador.model.dto;

import lombok.Data;

@Data
public class StatusPedidoDTO {
    private Long id_status_pedido;
    private String estado_pedido;

    public StatusPedidoDTO(){}
}
