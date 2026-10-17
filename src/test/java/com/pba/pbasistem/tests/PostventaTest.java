package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Postventa;
import java.time.LocalDate;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PostventaTest {

    private final Validator validador =
        Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void deberiaRegistrarReclamoCorrectamente() {
        Postventa reclamo = crearReclamo();

        assertTrue(validador.validate(reclamo).isEmpty());
        assertEquals(Postventa.Tipo.RECLAMO, reclamo.getTipo());
    }

    @Test
    public void deberiaCambiarEstadoAResuelto() {
        Postventa reclamo = crearReclamo();
        reclamo.setEstado(Postventa.Estado.RESUELTO);

        assertEquals(Postventa.Estado.RESUELTO, reclamo.getEstado());
    }

    @Test
    public void deberiaRegistrarFechaDeResolucion() {
        Postventa reclamo = crearReclamo();
        reclamo.setEstado(Postventa.Estado.RESUELTO);

        assertNotNull(reclamo.getFechaResolucion());
    }

    @Test
    public void fechaFuturaDeberiaFallar() {
        Postventa reclamo = crearReclamo();
        reclamo.setFecha(LocalDate.now().plusDays(1));

        assertFalse(validador.validate(reclamo).isEmpty());
    }

    @Test
    public void descripcionMuyCortaDeberiaFallar() {
        Postventa reclamo = crearReclamo();
        reclamo.setDescripcion("Corto");

        assertFalse(validador.validate(reclamo).isEmpty());
    }

    private Postventa crearReclamo() {
        Postventa reclamo = new Postventa();
        reclamo.setCliente(ClienteTest.crearCliente());
        reclamo.setTipo(Postventa.Tipo.RECLAMO);
        reclamo.setDescripcion("El envase llegó con una abolladura");
        return reclamo;
    }
}
