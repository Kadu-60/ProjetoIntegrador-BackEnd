package br.com.rd.ProjetoIntegrador.repository.Implementation;

import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import br.com.rd.ProjetoIntegrador.repository.ProdutoRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Produto> buscaAvancada(String cat, String marc, String fam, String prato) {
        Query sql = entityManager.createNativeQuery("SELECT * FROM PRODUTO where id_categoria "+cat+"   AND id_marca "+marc+" AND id_familia "+fam+" AND id_prato "+prato+"", Produto.class);
        List<Produto> list = sql.getResultList();
        return list;
    }
}
