package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.PrecoKey;
import lombok.Data;

@Data
public class Preco_VendaDTO {
    private PrecoKeyDTO precoKey;
    private double valor_preco;
}
