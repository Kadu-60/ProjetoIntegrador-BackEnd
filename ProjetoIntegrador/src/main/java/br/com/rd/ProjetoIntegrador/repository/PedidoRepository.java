package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.dto.PedidoDTO;
import br.com.rd.ProjetoIntegrador.model.entity.Cartao;
import br.com.rd.ProjetoIntegrador.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query(value = " SELECT * FROM pedido P WHERE p.id_cliente :id ", nativeQuery = true)
    public List<Pedido> findByIdCliente(@Param("id")Long id);
}
