package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.Preco_VendaDTO;
import br.com.rd.ProjetoIntegrador.service.PrecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preco")
public class PrecoController {
    @Autowired
    PrecoService precoService;

    @PostMapping
    public Preco_VendaDTO create(@RequestBody Preco_VendaDTO dto){
        return this.precoService.create(dto);
    }
    @GetMapping
    public List<Preco_VendaDTO> findAll(){
        return this.precoService.findAll();
    }
    @GetMapping("/{id}")
    public Preco_VendaDTO findById(@PathVariable("id") Long id){
        return this.precoService.findLastPriceById_produto(id);
    }
    @GetMapping("/findAllById_produto/{id}")
    public List<Preco_VendaDTO> findAllById_produto(@PathVariable("id") Long id){
        return this.precoService.findAllById_produto(id);
    }
}
