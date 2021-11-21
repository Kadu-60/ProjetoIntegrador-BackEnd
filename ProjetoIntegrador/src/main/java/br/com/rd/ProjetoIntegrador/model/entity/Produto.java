package br.com.rd.ProjetoIntegrador.model.entity;

import br.com.rd.ProjetoIntegrador.model.dto.Card.CardProdutoDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@SqlResultSetMapping(
        name = "CardProdutoDTO",
        classes =  @ConstructorResult(
                        targetClass = CardProdutoDTO.class, // nombre de la clase actual
                        columns = {
                                @ColumnResult(name = "id_produto", type = Long.class),
                                @ColumnResult(name = "foto"),
                                @ColumnResult(name = "nome_produto"),
                                @ColumnResult(name = "descricao"),
                                @ColumnResult(name = "valor_preco"),
                                @ColumnResult(name = "data_de_criacao"),
                                @ColumnResult(name = "destaque")
                        }
                )
)
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_produto;
    @Column(nullable = false)
    private String nome_produto;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private Integer ibu;
    @Column(nullable = false)
    private String cor;
    @Column(nullable = false)
    private Double teor;
    @Column(nullable = false)
    private Integer quantidade_ml;
    @Column(nullable = false)
    private String ean;
    @Column(nullable = false)
    private Boolean destaque;
    @Column(nullable = false)
    private Date dataDeCriacao;
    @Column(nullable = false)
    private String foto;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_familia")
    private Familia familia;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Categoria")
    private Categoria categoria;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_marca")
    private Marca marca;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_prato")
    private Prato prato;



}
