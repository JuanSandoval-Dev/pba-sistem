
package com.pba.pbasistem.model;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false)
    @Required
    private String nombre;
    
    @Column(nullable=false, unique=true)
    @Required
    private String usuario;
    
    @Column(nullable=false)
    @Required
    private String password;
    
    @Column(nullable=false)
    @Required
    @Stereotype("SELECT")
    private String rol; // Gerente, ventas, almacen, postventa
    
    // Getter y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
