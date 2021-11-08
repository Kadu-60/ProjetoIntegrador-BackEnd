package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
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
