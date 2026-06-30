package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Cliente;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private final Validator validador =
        Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void deberiaRegistrarClienteCorrectamente() {
        Cliente cliente = crearCliente();

        assertTrue(validador.validate(cliente).isEmpty());
        assertEquals("Amazonas", cliente.getRegion());
    }

    @Test
    public void noDeberiaGuardarClienteSinTipo() {
        Cliente cliente = crearCliente();
        cliente.setTipo(null);

        assertFalse(validador.validate(cliente).isEmpty());
    }

    @Test
    public void correoInvalidoDeberiaFallar() {
        Cliente cliente = crearCliente();
        cliente.setCorreo("correo-sin-arroba");

        assertFalse(validador.validate(cliente).isEmpty());
    }

    static Cliente crearCliente() {
        Cliente cliente = new Cliente();
        cliente.setRazonSocial("Asociación Agraria de Ejemplo");
        cliente.setTipo(Cliente.Tipo.AGRICULTOR);
        cliente.setRegion("Amazonas");
        cliente.setTelefono("987654321");
        cliente.setCorreo("contacto@ejemplo.pe");
        return cliente;
    }
}
