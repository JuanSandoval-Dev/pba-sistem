package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Insumo;
import com.pba.pbasistem.model.Proveedor;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InsumoTest {

    private final Validator validador =
        Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void deberiaAsociarProveedorConInsumo() {
        Proveedor proveedor = ProveedorTest.crearProveedor();
        Insumo insumo = new Insumo();
        insumo.setNombre("Cepa de Trichoderma");
        insumo.setTipo(Insumo.Tipo.CEPA);
        insumo.setStockActual(10);
        insumo.setStockMinimo(3);
        insumo.setProveedor(proveedor);

        assertSame(proveedor, insumo.getProveedor());
        assertTrue(validador.validate(insumo).isEmpty());
    }
}
