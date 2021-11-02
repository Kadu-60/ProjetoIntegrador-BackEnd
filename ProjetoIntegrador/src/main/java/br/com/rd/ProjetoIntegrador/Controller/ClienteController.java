package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.ClienteDTO;

import br.com.rd.ProjetoIntegrador.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cadastro-cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping
    public ClienteDTO criarCliente (@RequestBody ClienteDTO cliente){
        return clienteService.criarCliente(cliente);
    }

    @DeleteMapping("/{id_Cliente}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluirCliente (@PathVariable("id_Cliente") Long id_Cliente) {
        clienteService.excluirCliente(id_Cliente);
    }

    @GetMapping
    public List<ClienteDTO> findAllCliente(){
        return clienteService.findAllCliente();
    }
    @GetMapping("/{id}")
    public ClienteDTO findById(@PathVariable("id")Long id){
        return clienteService.findById(id);
    }

    @PutMapping("/{id_Cliente}")
    public ClienteDTO atualizarClientePorId (@RequestBody ClienteDTO clienteDTO, @PathVariable ("id_Cliente") Long id_Cliente){
        return  clienteService.atualizarClientePorId(clienteDTO, id_Cliente);
    }


}
