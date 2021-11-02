package br.com.rd.ProjetoIntegrador.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Parcelamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_parcelamento;
    @Column(nullable = false)
    private String parcelamento;
}
