package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.dto.ProdutoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Cartao;
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
}
