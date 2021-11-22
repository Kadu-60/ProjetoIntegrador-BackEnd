package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProdutoRepositoryCustom {
    List<CardProdutoDTO> buscaAvancada(String cat, String marc, String fam, String prato);

    CardProdutoDTO findCardProdutoById_produto(Long id);

    List<CardProdutoDTO> findCardsProdutoById_produto(List<Long> list);

    List<CardProdutoDTO> findCardsProdutoByNovidade();

    List<CardProdutoDTO> findCardsProdutoByDestaques();

    List<CardProdutoDTO> findCardsProdutoByBusca(String busca);


}
