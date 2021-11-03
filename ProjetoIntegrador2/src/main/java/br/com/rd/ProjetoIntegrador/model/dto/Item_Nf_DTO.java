package br.com.rd.ProjetoIntegrador.model.dto;

import br.com.rd.ProjetoIntegrador.model.Embeddeble.Item_Nf_Key;
import br.com.rd.ProjetoIntegrador.model.entity.Produto;
import lombok.Data;

import javax.persistence.*;

@Data
public class Item_Nf_DTO {
    private Item_Nf_KeyDTO item_nf_key;
    private ProdutoDTO produto;
    private Integer quantidade_produto;
    private Double desconto_produto;
    private Double icms;
    private Double cofins;
    private Double valor_unitario;
    private Double valor_total;
}
