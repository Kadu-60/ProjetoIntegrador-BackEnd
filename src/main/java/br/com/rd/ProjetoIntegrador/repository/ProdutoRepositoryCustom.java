package br.com.rd.ProjetoIntegrador.repository;

import br.com.rd.ProjetoIntegrador.model.entity.Produto;

import java.util.List;

public interface ProdutoRepositoryCustom {
    List<Produto> buscaAvancada(String cat, String marc, String fam, String prato);
}
