package br.com.rd.ProjetoIntegrador.model.dto.Card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CardProdutoDTO {
    private Long id_produto;
    private String foto;
    private String nome_produto;
    private String descricao;
    private Double valor_preco;

}
