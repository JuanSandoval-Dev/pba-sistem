package com.pba.pbasistem.model;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Table(name = "productos")
@Tabs({
    @Tab(properties = "nombre, categoria.nombre, unidadMedida, precioUnitario, stockActual, stockMinimo, cantidadVendida",
         defaultOrder = "${cantidadVendida} desc"),
    @Tab(name = "ProductosMasVendidos",
         properties = "nombre, categoria.nombre, stockActual, cantidadVendida",
         defaultOrder = "${cantidadVendida} desc")
})
@Getter
@Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @Size(max = 500)
    @Stereotype("MEMO")
    @Column(length = 500)
    private String descripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @DescriptionsList(descriptionProperties = "nombre")
    private Categoria categoria;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30)
    private String unidadMedida;

    @NotNull
    @DecimalMin("0.01")
    @Stereotype("MONEY")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Min(0)
    @Column(nullable = false)
    private int stockActual;

    @Min(0)
    @Column(nullable = false)
    private int stockMinimo;

    @Min(0)
    @ReadOnly
    @Column(nullable = false)
    private int cantidadVendida;

    public boolean estaBajoStockMinimo() {
        return stockActual < stockMinimo;
    }

    public void descontarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (cantidad > stockActual) {
            throw new IllegalArgumentException("No hay stock suficiente");
        }
        stockActual = stockActual - cantidad;
        cantidadVendida = cantidadVendida + cantidad;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
