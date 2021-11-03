package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.CategoriaDTO;
import br.com.rd.ProjetoIntegrador.model.dto.ProdutoDTO;
import br.com.rd.ProjetoIntegrador.service.CategoriaService;
import br.com.rd.ProjetoIntegrador.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/categorias")
    public List<CategoriaDTO> listarCategorias(){
        return categoriaService.listaDTO();
    }


    @GetMapping("/destaques")
    public List<ProdutoDTO> buscaDestaques(){
        return produtoService.buscaPorDestaque();
    }

    @GetMapping("/novidades")
    public List<ProdutoDTO> buscaNovidades(){
        return produtoService.buscaPorNovidade();
    }




}
