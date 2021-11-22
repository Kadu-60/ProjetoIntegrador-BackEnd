package br.com.rd.ProjetoIntegrador.Controller;

import antlr.collections.impl.LList;
import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
import br.com.rd.ProjetoIntegrador.model.dto.PratoDTO;
import br.com.rd.ProjetoIntegrador.model.dto.ProdutoDTO;
import br.com.rd.ProjetoIntegrador.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;


    @PostMapping
    public ProdutoDTO create(@RequestBody ProdutoDTO produto){
        return produtoService.addProduto(produto);
    }

    @GetMapping
    public List<ProdutoDTO> findAll(){
        return produtoService.findAllProduto();
    }
    @GetMapping("/{id}")
    public ProdutoDTO searchProdutoById(@PathVariable ("id") Long id){
        return  produtoService.searchByProdutoId(id);
    }



    @GetMapping("/por-marca/{id}")
    public List<ProdutoDTO> findByMarca(@PathVariable ("id") Long id){
        return produtoService.listaProdutoMarca(id);
    }

    @GetMapping("/por-categoria/{id}")
    public List<ProdutoDTO> findByCategoria(@PathVariable ("id") Long id){
        return produtoService.listaProdutoCategoria(id);
    }
    @GetMapping("/por-familia/{id}")
    public List<ProdutoDTO> findByFamilia(@PathVariable ("id") Long id){
        return produtoService.listaProdutoFamilia(id);
    }
    @GetMapping("/por-prato/{id}")
    public List<ProdutoDTO> findByPrato(@PathVariable ("id") Long id){
        return produtoService.listaProdutoPrato(id);
    }




    @GetMapping("/buscar/{busca}")
    public List<ProdutoDTO> pesquisarProdutos(@PathVariable("busca") String busca){
        return produtoService.pesquisarProdutos(busca);
    }
    @GetMapping("/destaques")
    public List<ProdutoDTO> buscaDestaques(){
        return produtoService.buscaPorDestaque();
    }

    @PutMapping("/{id}")
    public ProdutoDTO updateByProdutoId(@RequestBody ProdutoDTO dto, @PathVariable ("id") Long id){
        return  produtoService.updateByProdutoId(dto,id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteByProdutoId(@PathVariable("id") Long id){
        produtoService.deleteByProdutoId(id);
    }


}
