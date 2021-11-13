package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.NfDTO;
import br.com.rd.ProjetoIntegrador.model.dto.PedidoDTO;
import br.com.rd.ProjetoIntegrador.model.dto.StatusPedidoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Pedido;
import br.com.rd.ProjetoIntegrador.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Pedido")
public class PedidoController {
    @Autowired
    PedidoService pedidoService;

    @GetMapping
    public List<PedidoDTO> findAll(){
        return this.pedidoService.findAll();
    }

    @GetMapping("/{id}")
    public PedidoDTO findById(@PathVariable("id")Long id){
        return this.pedidoService.findById(id);
    }

    @GetMapping("cliente/{id}")
    public List<PedidoDTO> findByIdCliente(@PathVariable("id")Long id){
        return this.pedidoService.findByIdcliente(id);
    }

    @PutMapping("/{id}")
    public PedidoDTO updateStatusById(@RequestBody StatusPedidoDTO spd, @PathVariable("id") Long id){
        return this.pedidoService.updateStatusById(spd,id);
    }
    @DeleteMapping("/{id}")
    public void cancelamentoDePedido(@PathVariable("id") Long id){
        this.pedidoService.canceleById(id);
    }
    @PostMapping
    public PedidoDTO create(@RequestBody PedidoDTO dto){
        return this.pedidoService.create(dto);
    }

    @GetMapping("/gerarNf")
    public NfDTO gerarNf(@RequestBody PedidoDTO dto){
        return this.pedidoService.gerarNf(dto);
    }
}
