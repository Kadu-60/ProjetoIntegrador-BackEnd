package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Marca implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_marca;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String img;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
}
