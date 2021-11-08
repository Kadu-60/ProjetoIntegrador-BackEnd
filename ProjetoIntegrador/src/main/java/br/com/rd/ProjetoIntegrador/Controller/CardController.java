package br.com.rd.ProjetoIntegrador.Controller;

import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
import br.com.rd.ProjetoIntegrador.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Card")
public class CardController {
    @Autowired
    ProdutoService produtoService;


    @GetMapping("{id}")
    public CardProdutoDTO findCardProdutoById_produto(@PathVariable("id") Long id){
        return this.produtoService.findCardProdutoById_produto(id);
    }
}
