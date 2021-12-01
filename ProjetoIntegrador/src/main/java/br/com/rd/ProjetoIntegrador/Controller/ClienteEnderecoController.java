package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.ClienteEnderecoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.ClienteEndereco;
import br.com.rd.ProjetoIntegrador.service.ClienteEnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clienteEndereco")
public class ClienteEnderecoController {
    @Autowired
    ClienteEnderecoService clienteEnderecoService;


    @PostMapping("/create")
    ClienteEnderecoDTO create(@RequestBody ClienteEnderecoDTO dto){
        return this.clienteEnderecoService.create(dto);
    }

    @GetMapping("/cliente/{id}")
    List<ClienteEnderecoDTO> findAllByIdCliente(@PathVariable("id")Long id){
        return this.clienteEnderecoService.findByIdCliente(id);
    }

    @PutMapping("/EndPrincipal/{id_cliente}/{id_endereco}")
    List<ClienteEnderecoDTO> updateEndPrincipal(@PathVariable("id_cliente")Long id_cliente, @PathVariable("id_endereco")Long id_endereco){
        return this.clienteEnderecoService.updateEndPrincipal(id_cliente, id_endereco);
    }

    @PutMapping("/EndEntrega/{id_cliente}/{id_endereco}")
    List<ClienteEnderecoDTO> updateEndEntrega(@PathVariable("id_cliente")Long id_cliente, @PathVariable("id_endereco")Long id_endereco){
        return this.clienteEnderecoService.updateEndEntrega(id_cliente, id_endereco);
    }

    @DeleteMapping("/DeleteEndereco/{id_cliente}/{id_endereco}")
    void delete(@PathVariable("id_cliente")Long id_cliente, @PathVariable("id_endereco")Long id_endereco){
        this.clienteEnderecoService.delete(id_cliente,id_endereco);
    }


}
