package br.com.rd.ProjetoIntegrador.repository.Implementation;

import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
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

    @Override
    public CardProdutoDTO findCardProdutoById_produto(Long id) {
        Query q =entityManager.createNativeQuery("select pv.id_produto, p.foto, p.nome_produto, p.descricao, pv.valor_preco from produto p inner join preco_venda pv on (p.id_produto =pv.id_produto) where pv.id_produto = :id order by pv.data_vigencia limit 1","CardProdutoDTO");
        q.setParameter("id",id);
        List<CardProdutoDTO> list= q.getResultList();
        for (CardProdutoDTO c : list){
            return c;
        }
        return null;
    }

}
