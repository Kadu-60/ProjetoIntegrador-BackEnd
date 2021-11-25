package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
import br.com.rd.ProjetoIntegrador.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Card")
public class CardController {
    @Autowired
    ProdutoService produtoService;


    @GetMapping("{id}")
    public CardProdutoDTO findCardProdutoById_produto(@PathVariable("id") Long id){
        return this.produtoService.findCardProdutoById_produto(id);
    }
    @GetMapping("/multi")
    public List<CardProdutoDTO> findCardsProfutoById_produto(@RequestBody List<Long> list){
        return this.produtoService.findCardsProfutoById_produto(list);
    }
    @PostMapping("/multi")
    public List<CardProdutoDTO> pfindCardsProfutoById_produto(@RequestBody List<Long> list){
        return this.produtoService.findCardsProfutoById_produto(list);
    }
    @GetMapping("/{id_cat}/{id_marc}/{id_fam}/{id_prato}")
    public List<CardProdutoDTO> findBuscaAvan(@PathVariable ("id_cat")Long id_cat, @PathVariable ("id_marc")Long id_marc, @PathVariable ("id_fam")Long id_fam, @PathVariable ("id_prato")Long id_prato){
        return this.produtoService.buscaAvan(id_cat, id_marc, id_fam, id_prato);
    }
    @GetMapping("/novidades")
    public List<CardProdutoDTO> findCardsProfutoByNovidade(){
        return this.produtoService.findCardsProfutoByNovidade();
    }

    @GetMapping("/destaques")
    public List<CardProdutoDTO> findCardsProdutoByDestaques(){
        return this.produtoService.findCardsProdutoByDestaques();
    }

    @GetMapping("/busca/{busca}")
    public List<CardProdutoDTO> findCardsProdutoByBusca(@PathVariable("busca") String busca){
        return this.produtoService.findCardsProdutoByBusca(busca);
    }
    @GetMapping("/Marca/{id}")
    public List<CardProdutoDTO> findCardsProdutoByIdMarca(@PathVariable("id") Long id){
        return this.produtoService.findCardsProdutoByIdMarca(id);
    }


}
