package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryCustom {
    @Query(value = " SELECT * FROM PRODUTO P WHERE p.ID_MARCA =? ", nativeQuery = true)
    List<Produto> findByMarca(Long id);


    @Query(value = " SELECT * FROM PRODUTO P WHERE p.ID_CATEGORIA =? ", nativeQuery = true)
    List<Produto> findByCategoria(Long id);

    @Query(value = " SELECT * FROM PRODUTO P WHERE p.ID_FAMILIA =? ", nativeQuery = true)
    List<Produto> findByFamilia(Long id);

    @Query(value = " SELECT * FROM PRODUTO P WHERE p.ID_PRATO =? ", nativeQuery = true)
    List<Produto> findByPrato(Long id);

    @Query(value = " SELECT * FROM PRODUTO P WHERE p.NOME_PRODUTO LIKE %:busca% ", nativeQuery = true)
    List<Produto> pesquisarProdutos(@Param("busca") String busca);

    @Query(value = "SELECT * FROM PRODUTO P ORDER BY P.data_de_criacao asc LIMIT 5", nativeQuery = true)
    List<Produto> buscaNovidades();

    List<Produto> findByDestaqueTrue();

//    @Query(value= "select pv.id_produto, p.foto, p.nome_produto, p.descricao, pv.valor_preco from produto p inner join preco_venda pv on (p.id_produto =pv.id_produto) where pv.id_produto = :id order by pv.data_vigencia limit 1", nativeQuery = true)
//    @Query(value= "select pv.id_produto, p.foto, p.nome_produto, p.descricao, pv.valor_preco from produto p inner join preco_venda pv on (p.id_produto =pv.id_produto) where pv.id_produto = :id order by pv.data_vigencia limit 1", name = "CardProdutoDTO", nativeQuery = true)
//    CardProdutoDTO findCardProdutoById_produto(@Param("id")Long id);
}
