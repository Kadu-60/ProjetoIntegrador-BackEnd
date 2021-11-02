package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.CategoriaDTO;
import br.com.rd.ProjetoIntegrador.model.dto.MarcaDTO;
import br.com.rd.ProjetoIntegrador.service.CategoriaService;
import br.com.rd.ProjetoIntegrador.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriasController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaDTO> findCategoriasMarca(){
        return categoriaService.listaDTO();
    }
}
