package com.pba.pbasistem.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.DescriptionsList;

@Entity
@Table(name = "insumos")
@Getter
@Setter
public class Insumo {

    public enum Tipo {
        CEPA, ENVASE, NUTRIENTE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Tipo tipo;

    @Min(0)
    @Column(nullable = false)
    private int stockActual;

    @Min(0)
    @Column(nullable = false)
    private int stockMinimo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false)
    @DescriptionsList(descriptionProperties = "nombre")
    private Proveedor proveedor;

    public boolean estaBajoStockMinimo() {
        return stockActual < stockMinimo;
    }

}
