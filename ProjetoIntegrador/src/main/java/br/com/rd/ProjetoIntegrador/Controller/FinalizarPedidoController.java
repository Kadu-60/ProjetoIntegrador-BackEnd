package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.PedidoComArrayItemPedidoDTO;
import br.com.rd.ProjetoIntegrador.service.FinalizarPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finalizarPedido")
public class FinalizarPedidoController {

    @Autowired
    FinalizarPedidoService finalizarPedidoService;

    @PostMapping
    public PedidoComArrayItemPedidoDTO finalizarPedido(@RequestBody PedidoComArrayItemPedidoDTO pedidoComArrayItemPedidoDTO){
        return finalizarPedidoService.finalizarPedido(pedidoComArrayItemPedidoDTO);
    }
}
