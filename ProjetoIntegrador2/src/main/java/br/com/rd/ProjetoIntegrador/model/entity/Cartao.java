package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Cartao;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private Date validade;

}
