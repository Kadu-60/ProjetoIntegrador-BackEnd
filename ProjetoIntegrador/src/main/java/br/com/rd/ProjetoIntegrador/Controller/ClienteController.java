package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.ClienteDTO;

import br.com.rd.ProjetoIntegrador.service.ClienteService;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.auth0.jwt.JWT;

import java.util.List;
import java.util.Map;

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
