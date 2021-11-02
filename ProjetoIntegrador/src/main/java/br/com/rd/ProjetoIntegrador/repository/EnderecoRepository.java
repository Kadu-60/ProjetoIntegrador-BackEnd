package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.entity.Cartao;
import br.com.rd.ProjetoIntegrador.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
