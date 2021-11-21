package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.PrecoKey;
import br.com.rd.ProjetoIntegrador.model.dto.Preco_VendaDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Preco_venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecoRepository extends JpaRepository<Preco_venda, PrecoKey> {
    @Query(value = "select * from preco_venda where id_produto = :id order by data_vigencia", nativeQuery = true)
    public List<Preco_venda> findAllById_produto(@Param("id") Long id_produto);
    @Query(value = "select * from preco_venda where id_produto = :id order by data_vigencia desc limit 1", nativeQuery = true)
    public Preco_venda findLastPriceById_produto(@Param("id") Long id_produto);
}
