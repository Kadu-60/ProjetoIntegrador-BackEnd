package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_Nf_Key;
import br.com.rd.ProjetoIntegrador.model.entity.Item_Nf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Item_NfRepository extends JpaRepository<Item_Nf, Item_Nf_Key> {
}
