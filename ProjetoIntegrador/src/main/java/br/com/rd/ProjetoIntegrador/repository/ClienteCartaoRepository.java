package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.ClienteCartaoKey;
import br.com.rd.ProjetoIntegrador.model.entity.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, ClienteCartaoKey> {
    @Query(value="select * from cliente_cartao where id_cliente = :id", nativeQuery = true)
    List<ClienteCartao> findAllByIdCliente(@Param("id") Long id);
}
