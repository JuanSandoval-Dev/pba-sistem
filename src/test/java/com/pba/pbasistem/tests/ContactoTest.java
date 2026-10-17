package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.Cliente;
import com.pba.pbasistem.model.Contacto;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactoTest {

    private final Validator validador =
        Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void deberiaAsociarContactoConCliente() {
        Cliente cliente = ClienteTest.crearCliente();
        Contacto contacto = new Contacto();
        contacto.setNombre("María López");
        contacto.setCargo("Encargada de compras");
        contacto.setTelefono("987123456");
        contacto.setCorreo("maria@ejemplo.pe");

        cliente.agregarContacto(contacto);

        assertSame(cliente, contacto.getCliente());
        assertTrue(validador.validate(contacto).isEmpty());
    }

    @Test
    public void telefonoInvalidoDeberiaFallar() {
        Cliente cliente = ClienteTest.crearCliente();
        Contacto contacto = new Contacto();
        contacto.setNombre("María López");
        contacto.setCargo("Compras");
        contacto.setTelefono("abc");
        contacto.setCliente(cliente);

        assertFalse(validador.validate(contacto).isEmpty());
    }
}
