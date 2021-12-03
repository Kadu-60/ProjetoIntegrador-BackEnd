package br.com.rd.ProjetoIntegrador.service;

import br.com.rd.ProjetoIntegrador.model.dto.Item_pedidoDTO;
import br.com.rd.ProjetoIntegrador.model.dto.PedidoComArrayItemPedidoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class FinalizarPedidoService {
    @Autowired
    PedidoService pedidoService;
    @Autowired
    ItemPedidoService item_pedidoService;
    public PedidoComArrayItemPedidoDTO finalizarPedido(PedidoComArrayItemPedidoDTO pedidoComArrayItemPedidoDTO) {
        PedidoComArrayItemPedidoDTO retorno = new PedidoComArrayItemPedidoDTO();
        retorno.setPedido(pedidoService.create(pedidoComArrayItemPedidoDTO.getPedido()));
        retorno.setArrayItens(new ArrayList<>());
        for(Item_pedidoDTO item : pedidoComArrayItemPedidoDTO.getArrayItens()){
            item.getItem_pedido_key().setPedido(retorno.getPedido());
            retorno.getArrayItens().add(item_pedidoService.create(item));
        }
        return retorno;
    }
}
