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

    @Transient
    private Producto productoAnterior;

    @Transient
    private int cantidadAnterior;

    @PostLoad
    public void guardarValoresAnteriores() {
        productoAnterior = producto;
        cantidadAnterior = cantidad;
        ventaRegistrada = true;
    }

    @PrePersist
    public void antesDeGuardar() {
        registrarVenta();
        actualizarTotalPedido();
    }

    @PreUpdate
    public void antesDeActualizar() {
        registrarVenta();
        actualizarTotalPedido();
    }

    @PreRemove
    public void antesDeEliminar() {
        if (!ventaRegistrada) {
            return;
        }

        Producto productoADevolver =
            productoAnterior != null ? productoAnterior : producto;
        int cantidadADevolver =
            cantidadAnterior > 0 ? cantidadAnterior : cantidad;

        productoADevolver.devolverStock(cantidadADevolver);
        ventaRegistrada = false;
    }

    public void registrarVenta() {
        validarDatos();

        if (!ventaRegistrada) {
            producto.descontarStock(cantidad);
        }
        else {
            ajustarStock();
        }

        calcularSubtotal();
        productoAnterior = producto;
        cantidadAnterior = cantidad;
        ventaRegistrada = true;
    }

    private void ajustarStock() {
        if (esMismoProducto(productoAnterior, producto)) {
            int diferencia = cantidad - cantidadAnterior;
            if (diferencia > 0) {
                producto.descontarStock(diferencia);
            }
            else if (diferencia < 0) {
                producto.devolverStock(-diferencia);
            }
            return;
        }

        producto.validarStockDisponible(cantidad);
        if (productoAnterior != null && cantidadAnterior > 0) {
            productoAnterior.devolverStock(cantidadAnterior);
        }
        producto.descontarStock(cantidad);
    }

    private boolean esMismoProducto(Producto anterior, Producto actual) {
        if (anterior == actual) {
            return true;
        }
        return anterior != null
            && actual != null
            && anterior.getId() != null
            && anterior.getId().equals(actual.getId());
    }

    private void validarDatos() {
        if (producto == null) {
            throw new IllegalStateException("Debe seleccionar un producto");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        if (precioUnitario == null) {
            precioUnitario = producto.getPrecioUnitario();
        }
        if (precioUnitario == null
                || precioUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "El precio unitario debe ser mayor que cero");
        }
    }

    private void calcularSubtotal() {
        subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    private void actualizarTotalPedido() {
        if (pedido != null) {
            pedido.calcularTotal();
        }
    }

}
