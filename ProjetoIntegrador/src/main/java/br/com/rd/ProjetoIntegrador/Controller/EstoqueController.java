package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.EstoqueDTO;
import br.com.rd.ProjetoIntegrador.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Estoque")
@CrossOrigin(origins = "http://localhost:3000")
public class EstoqueController {
    @Autowired
    EstoqueService estoqueService;

    @GetMapping("/{id}")
    public EstoqueDTO getById(@PathVariable("id") Long id){
        return this.estoqueService.getById(id);
    }
    @GetMapping
    public List<EstoqueDTO> findAll(){
        return this.estoqueService.findAll();
    }
    @PutMapping("/{id}")
    public EstoqueDTO updateById(@PathVariable("id") Long id,@RequestBody EstoqueDTO estoqueDTO){
        return this.estoqueService.update(estoqueDTO, id);
    }
    @PostMapping
    public EstoqueDTO create(@RequestBody EstoqueDTO estoqueDTO){
        return this.estoqueService.create(estoqueDTO);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Long id){
        this.estoqueService.delete(id);
    }

}
