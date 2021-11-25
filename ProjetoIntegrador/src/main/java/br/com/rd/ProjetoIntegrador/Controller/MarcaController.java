package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.MarcaDTO;
import br.com.rd.ProjetoIntegrador.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Marca")
public class MarcaController {
    @Autowired
    MarcaService marcaService;

    @GetMapping
    public List<MarcaDTO> findMarcasCategoria(){
        return marcaService.listaDTO();

    }
    @GetMapping("{id}")
    public MarcaDTO getById(@PathVariable("id")Long id){
        return marcaService.buscarPorId(id);
    }
}
