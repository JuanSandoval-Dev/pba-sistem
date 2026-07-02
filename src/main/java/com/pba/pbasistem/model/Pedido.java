package com.pba.pbasistem.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Table(name = "pedidos")
@Tabs({
    @Tab(properties = "id, fecha, cliente.razonSocial, canalVenta, estado, total",
         defaultOrder = "${fecha} desc"),
    @Tab(name = "PedidosPorEstado",
         properties = "estado, id, fecha, cliente.razonSocial, total",
         defaultOrder = "${estado}, ${fecha} desc")
})
@View(members =
    "fecha, cliente; canalVenta, estado; "
    + "detalles { detalles }; "
    + "total")
@View(name = "Factura", members =
    "DATOS DEL PEDIDO [ id, fecha; cliente; canalVenta, estado ]; "
    + "DETALLE DE PRODUCTOS [ detalles ]; "
    + "TOTAL [ total ]")
@Getter
@Setter
public class Pedido {

    public enum CanalVenta {
        WEB, WHATSAPP, DISTRIBUIDOR
    }

    public enum Estado {
        PENDIENTE, ENVIADO, ENTREGADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha = LocalDate.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @DescriptionsList(descriptionProperties = "razonSocial")
    private Cliente cliente;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CanalVenta canalVenta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado = Estado.PENDIENTE;

    @Valid
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @ListProperties("producto.nombre, cantidad, precioUnitario, subtotal")
    private List<DetallePedido> detalles = new ArrayList<>();

    @ReadOnly
    @Stereotype("MONEY")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    public void agregarDetalle(DetallePedido detalle) {
        detalle.setPedido(this);
        detalle.registrarVenta();
        detalles.add(detalle);
        calcularTotal();
    }

    @PrePersist
    @PreUpdate
    public void calcularTotal() {
        total = BigDecimal.ZERO;
        for (DetallePedido detalle : detalles) {
            if (detalle.getSubtotal() != null) {
                total = total.add(detalle.getSubtotal());
            }
        }
    }

}
