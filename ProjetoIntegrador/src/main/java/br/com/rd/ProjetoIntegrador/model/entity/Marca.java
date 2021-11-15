package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_marca;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String img;
    @Column(nullable = false)
    private String desc;
}
