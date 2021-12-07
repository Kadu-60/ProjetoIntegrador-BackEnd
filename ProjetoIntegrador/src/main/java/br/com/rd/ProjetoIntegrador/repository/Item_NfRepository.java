package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_Nf_Key;
import br.com.rd.ProjetoIntegrador.model.entity.Item_Nf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Item_NfRepository extends JpaRepository<Item_Nf, Item_Nf_Key> {
    @Query(value = "select * from item_nf  where id_nf = :id", nativeQuery = true)
    List<Item_Nf> getById_nf(@Param("id") Long id);
}
