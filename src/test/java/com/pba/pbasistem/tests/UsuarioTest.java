
package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {
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
    
}
