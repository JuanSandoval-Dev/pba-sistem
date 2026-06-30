package com.pba.pbasistem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "proveedores")
@Getter
@Setter
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
    @Column(nullable = false, unique = true, length = 11)
    private String ruc;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String telefono;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String tipoInsumoQueProvee;

    @Override
    public String toString() {
        return nombre;
    }
}
