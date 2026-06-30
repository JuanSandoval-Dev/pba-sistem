package com.pba.pbasistem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categorias")
@Getter
@Setter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, unique = true, length = 60)
    private String nombre; // Bioinoculante, bioinsecticida, abono foliar, sustrado´solido

    @Size(max = 250)
    @Column(length = 250)
    private String descripcion;

    @Override
    public String toString() {
        return nombre;
    }
}
