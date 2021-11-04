package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
public class Nf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_nf;
    @Column(nullable = false, unique = true)
    private Long chave_acesso;
    @Column(nullable = false)
    private String serie;
    @Column(nullable = false)
    private Date emissao;
    @Column(nullable = false)
    private Double subtotal;
    @Column(nullable = false)
    private Double total;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Cliente")
    private Cliente cliente;

}
