package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Proveedor;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProveedorTest {

    private final Validator validador =
        Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void deberiaCrearProveedorCorrectamente() {
        Proveedor proveedor = crearProveedor();

        assertTrue(validador.validate(proveedor).isEmpty());
        assertEquals("20123456789", proveedor.getRuc());
    }

    static Proveedor crearProveedor() {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Proveedor de ejemplo");
        proveedor.setRuc("20123456789");
        proveedor.setTelefono("987654321");
        proveedor.setTipoInsumoQueProvee("Cepas");
        return proveedor;
    }
}
