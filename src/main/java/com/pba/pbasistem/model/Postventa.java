package com.pba.pbasistem.model;

import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;

@Entity
@Table(name = "postventas")
@Tabs({
    @Tab(properties = "fecha, cliente.razonSocial, tipo, estado, fechaResolucion",
         defaultOrder = "${fecha} desc"),
    @Tab(name = "ReclamosPorEstado",
         properties = "estado, fecha, cliente.razonSocial, descripcion, fechaResolucion",
         baseCondition = "${tipo} = 'RECLAMO'",
         defaultOrder = "${estado}, ${fecha} desc")
})
@Getter
@Setter
public class Postventa {

    public enum Tipo {
        RECLAMO, CONSULTA, RESULTADO_DE_CAMPO
    }

    public enum Estado {
        PENDIENTE, RESUELTO
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
    @Column(nullable = false, length = 30)
    private Tipo tipo;

    @NotBlank
    @Size(max = 1000)
    @Stereotype("MEMO")
    @Column(nullable = false, length = 1000)
    private String descripcion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado = Estado.PENDIENTE;

    @ReadOnly
    private LocalDate fechaResolucion;

    @PrePersist
    @PreUpdate
    public void actualizarFechaResolucion() {
        if (estado == Estado.RESUELTO && fechaResolucion == null) {
            fechaResolucion = LocalDate.now();
        }
    }

    // Este setter sí tiene lógica porque registra la fecha de resolución
    public void setEstado(Estado estado) {
        this.estado = estado;
        actualizarFechaResolucion();
    }
}
