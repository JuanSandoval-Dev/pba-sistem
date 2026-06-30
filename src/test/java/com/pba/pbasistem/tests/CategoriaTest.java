package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Categoria;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CategoriaTest {

    @Test
    public void testCategoriaSeCreaCorrectamente() {
        Categoria c = new Categoria();
        c.setNombre("Bioinoculante");
        c.setDescripcion("Controladores microbiologicos");

        assertEquals("Bioinoculante", c.getNombre());
        assertNotNull(c.getDescripcion());
    }

    @Test
    public void testCategoriaConNombreVacio() {
        Categoria c = new Categoria();
        c.setNombre("");
        c.setDescripcion("Sin nombre");

        assertEquals("", c.getNombre());
    }

    @Test
    public void testCategoriasSonDiferentes() {
        Categoria c1 = new Categoria();
        c1.setNombre("Bioinoculante");

        Categoria c2 = new Categoria();
        c2.setNombre("Bioinsecticida");

        assertNotEquals(c1.getNombre(), c2.getNombre());
    }
}
