package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_pedido_key;
import br.com.rd.ProjetoIntegrador.model.dto.PedidoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Item_pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Item_pedidoRepository extends JpaRepository<Item_pedido, Item_pedido_key> {
    @Query(value = "SELECT * FROM item_pedido WHERE id_pedido = :pedido", nativeQuery = true)
    public List<Item_pedido> findById_pedido(@Param("pedido")Long id);
}
