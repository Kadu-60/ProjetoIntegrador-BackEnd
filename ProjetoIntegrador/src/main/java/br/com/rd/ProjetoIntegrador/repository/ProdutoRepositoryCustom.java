package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepositoryCustom {
    List<Produto> buscaAvancada(String cat, String marc, String fam, String prato);

    CardProdutoDTO findCardProdutoById_produto(@Param("id")Long id);
}
