package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Categoria;
import com.pba.pbasistem.model.Producto;
import java.math.BigDecimal;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoTest {

    private final Validator validador =
        Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void deberiaGuardarProductoCorrectamente() {
        Producto producto = crearProducto();

        assertTrue(validador.validate(producto).isEmpty());
        assertEquals("Bio Control", producto.getNombre());
    }

    @Test
    public void deberiaDetectarStockDebajoDelMinimo() {
        Producto producto = crearProducto();
        producto.setStockActual(4);
        producto.setStockMinimo(10);

        assertTrue(producto.estaBajoStockMinimo());
    }

    private Producto crearProducto() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Bioinsecticida");

        Producto producto = new Producto();
        producto.setNombre("Bio Control");
        producto.setDescripcion("Producto de ejemplo");
        producto.setCategoria(categoria);
        producto.setUnidadMedida("Frasco 1 L");
        producto.setPrecioUnitario(new BigDecimal("45.00"));
        producto.setStockActual(20);
        producto.setStockMinimo(5);
        return producto;
    }
}
