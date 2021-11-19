package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
import br.com.rd.ProjetoIntegrador.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Card")
@CrossOrigin(origins = "http://localhost:3000")
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
}
