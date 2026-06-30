package com.pba.pbasistem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.DisplaySize;
import org.openxava.annotations.Stereotype;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false, unique = true, length = 40)
    private String usuario;

    @NotBlank
    @Size(min = 4, max = 100)
    @DisplaySize(20)
    @Stereotype("PASSWORD")
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @NotBlank
    @Pattern(regexp = "Gerente|Ventas|Almacén|Postventa",
            message = "Seleccione un rol válido")
    @Column(nullable = false, length = 20)
    private String rol;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
