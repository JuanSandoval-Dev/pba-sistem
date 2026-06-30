package com.pba.pbasistem.model;

import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.ReadOnly;
import org.openxava.annotations.Stereotype;

@Entity
@Table(name = "detalles_pedido")
@Getter
@Setter
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @DescriptionsList(descriptionProperties = "nombre")
    private Producto producto;

    @Min(1)
    @Column(nullable = false)
    private int cantidad;

    @NotNull
    @DecimalMin("0.01")
    @Stereotype("MONEY")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @ReadOnly
    @Stereotype("MONEY")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO;

    @Transient
    private boolean ventaRegistrada;

    @PrePersist
    public void antesDeGuardar() {
        registrarVenta();
        if (pedido != null) {
            pedido.calcularTotal();
        }
    }

    public void registrarVenta() {
        if (ventaRegistrada) {
            return;
        }
        if (producto == null) {
            throw new IllegalStateException("Debe seleccionar un producto");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (precioUnitario == null) {
            precioUnitario = producto.getPrecioUnitario();
        }

        subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
        producto.descontarStock(cantidad);
        ventaRegistrada = true;
    }

}
