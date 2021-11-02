package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.entity.Parcelamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParcelamentoRepository extends JpaRepository<Parcelamento, Long> {
}
