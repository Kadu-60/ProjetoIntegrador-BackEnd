package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.EnderecoDTO;
import br.com.rd.ProjetoIntegrador.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/Endereco")
public class EnderecoController {
    @Autowired
    EnderecoService enderecoService;

    @GetMapping
    public List<EnderecoDTO> findAll(){
        return this.enderecoService.findAll();
    }
    @GetMapping("/{id}")
    public EnderecoDTO findById( @PathVariable("id") Long id){
        return this.enderecoService.findById(id);
    }
    @PostMapping
    public EnderecoDTO create(@Valid @RequestBody EnderecoDTO dto ){
        return this.enderecoService.create(dto);
    }
    @PutMapping("{id}")
    public EnderecoDTO update(@PathVariable("id") Long id, @RequestBody EnderecoDTO dto){
        return this.enderecoService.updateById(id, dto);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id){
        this.enderecoService.deleteById(id);
    }

}
