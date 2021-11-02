package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import lombok.Data;

@Data
public class EstoqueDTO {
    private EstoqueKeyDTO estoqueKey;
    private Integer quantidade;
}
