package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    private final Validator validador =
        Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void deberiaRegistrarPedidoCorrectamente() {
        Pedido pedido = crearPedido();

        assertTrue(validador.validate(pedido).isEmpty());
        assertEquals(Pedido.Estado.PENDIENTE, pedido.getEstado());
    }

    @Test
    public void deberiaCalcularTotalDelPedido() {
        Pedido pedido = crearPedido();
        pedido.agregarDetalle(
            DetallePedidoTest.crearDetalle(
                DetallePedidoTest.crearProducto("Producto A", "10.00", 20), 2));
        pedido.agregarDetalle(
            DetallePedidoTest.crearDetalle(
                DetallePedidoTest.crearProducto("Producto B", "15.00", 20), 3));

        assertEquals(0, new BigDecimal("65.00").compareTo(pedido.getTotal()));
    }

    @Test
    public void fechaFuturaDeberiaFallar() {
        Pedido pedido = crearPedido();
        pedido.setFecha(LocalDate.now().plusDays(1));

        assertFalse(validador.validate(pedido).isEmpty());
    }

    static Pedido crearPedido() {
        Pedido pedido = new Pedido();
        pedido.setCliente(ClienteTest.crearCliente());
        pedido.setCanalVenta(Pedido.CanalVenta.WHATSAPP);
        return pedido;
    }
}
