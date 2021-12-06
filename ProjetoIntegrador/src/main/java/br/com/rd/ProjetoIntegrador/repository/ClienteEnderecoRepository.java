package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.ClienteEnderecoKey;
import br.com.rd.ProjetoIntegrador.model.entity.ClienteEndereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteEnderecoRepository extends JpaRepository<ClienteEndereco, ClienteEnderecoKey> {
    @Query(value="select * from cliente_endereco where id_cliente = :id", nativeQuery = true)
    List<ClienteEndereco> getByIdCliente(@Param("id") Long id);
}
