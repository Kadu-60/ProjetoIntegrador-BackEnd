package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.FormularioContatoDTO;
import br.com.rd.ProjetoIntegrador.service.FormularioContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/formulariocontato")
@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class FormularioContatoController {

    @Autowired
    FormularioContatoService formularioContatoService;

    @PostMapping
    public @ResponseBody FormularioContatoDTO create(@RequestBody FormularioContatoDTO formulariocontato) {
        return formularioContatoService.createFormularioContato(formulariocontato);
    }

    @GetMapping
    public @ResponseBody List<FormularioContatoDTO> findAll() {
        return formularioContatoService.findAllFormularioContato();
    }

    @PutMapping("/{id}")
    public @ResponseBody FormularioContatoDTO updateById(@RequestBody FormularioContatoDTO dto, @PathVariable("id") Long id) {
        return formularioContatoService.updateById(dto, id);
    }

   @GetMapping("/{id}")
   public @ResponseBody FormularioContatoDTO searchById(@PathVariable("id") Long id) {
       return formularioContatoService.searchById(id);
   }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteById(@PathVariable("id") Long id) {
        formularioContatoService.deleteById(id);
        return "200";
    }
}

