package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class MetodoPag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_metodoPag;
    @Column(nullable = false)
    private String metodoPag;
}
