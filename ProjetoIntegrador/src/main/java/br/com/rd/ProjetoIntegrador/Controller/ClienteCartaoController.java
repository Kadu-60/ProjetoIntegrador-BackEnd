package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.ClienteCartaoDTO;
import br.com.rd.ProjetoIntegrador.service.ClienteCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienteCartao")
public class ClienteCartaoController {
    @Autowired
    ClienteCartaoService clienteCartaoService;
    @PostMapping("/create")
    ClienteCartaoDTO create(@RequestBody ClienteCartaoDTO dto){
        return this.clienteCartaoService.create(dto);
    }

    @GetMapping("/cliente/{id}")
    List<ClienteCartaoDTO> findByIdCliente(@PathVariable("id")Long id){
        return this.clienteCartaoService.findById_Cliente(id);
    }

    @PutMapping("/tornarPrincipal/{id1}/{id2}")
    List<ClienteCartaoDTO> tornarPrincipal(@PathVariable("id1")Long id_Cliente, @PathVariable("id2")Long id_cartao){
        return this.clienteCartaoService.tornarPrincipal(id_Cliente, id_cartao);
    }
    @DeleteMapping("/delete/{id1}/{id2}")
    void delete(@PathVariable("id1") Long id_cliente,@PathVariable("id2") Long id_cartao){
        this.clienteCartaoService.delete(id_cliente, id_cartao);
    }
}
