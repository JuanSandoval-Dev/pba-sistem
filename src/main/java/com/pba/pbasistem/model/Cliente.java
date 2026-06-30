package com.pba.pbasistem.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.Stereotype;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Cliente {

    public enum Tipo {
        AGRICULTOR, DISTRIBUIDOR, TIENDA_AGRICOLA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String razonSocial;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private Tipo tipo;

    @NotBlank
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String region;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String telefono;

    @NotBlank
    @Email
    @Stereotype("EMAIL")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String correo;

    @Valid
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contacto> contactos = new ArrayList<>();

    public void agregarContacto(Contacto contacto) {
        contactos.add(contacto);
        contacto.setCliente(this);
    }

    @Override
    public String toString() {
        return razonSocial;
    }
}
