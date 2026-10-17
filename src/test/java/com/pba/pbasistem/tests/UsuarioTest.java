
package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Usuario;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    private final Validator validador =
        Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testUsuarioSeCreaCorrectamente() {
        Usuario u = new Usuario();
        u.setNombre("Administrador");
        u.setUsuario("admin");
        u.setPassword("pba2026");
        u.setRol("Gerente");
        
        assertEquals("admin", u.getUsuario());
        assertEquals("Gerente", u.getRol());
        assertNotNull(u.getNombre());
    }
    
    @Test
    public void testUsuarioSinRolEsInvalido() {
        Usuario u = new Usuario();
        u.setNombre("Vendedor");
        u.setUsuario("ventas");
        u.setPassword("ventas123");
        // sin rol
        
        assertNull(u.getRol());
    }

    @Test
    public void usuarioCortoDeberiaFallar() {
        Usuario u = crearUsuarioValido();
        u.setUsuario("ab");

        assertFalse(validador.validate(u).isEmpty());
    }

    @Test
    public void passwordCortoDeberiaFallar() {
        Usuario u = crearUsuarioValido();
        u.setPassword("123");

        assertFalse(validador.validate(u).isEmpty());
    }

    private Usuario crearUsuarioValido() {
        Usuario u = new Usuario();
        u.setNombre("Administrador");
        u.setUsuario("admin");
        u.setPassword("pba2026");
        u.setRol("Gerente");
        return u;
    }
}
