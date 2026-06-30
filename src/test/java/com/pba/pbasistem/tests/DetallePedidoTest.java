package com.pba.pbasistem.tests;

import com.pba.pbasistem.model.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DetallePedidoTest {

    @Test
    public void deberiaDescontarStockAlAgregarDetalle() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto producto = crearProducto("Bio Control", "25.00", 20);
        DetallePedido detalle = crearDetalle(producto, 3);

        pedido.agregarDetalle(detalle);

        assertEquals(17, producto.getStockActual());
        assertEquals(3, producto.getCantidadVendida());
        assertEquals(0, new BigDecimal("75.00").compareTo(detalle.getSubtotal()));
    }

    static Producto crearProducto(String nombre, String precio, int stock) {
        Categoria categoria = new Categoria();
        categoria.setNombre("Bioinsecticida");

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setCategoria(categoria);
        producto.setUnidadMedida("Frasco");
        producto.setPrecioUnitario(new BigDecimal(precio));
        producto.setStockActual(stock);
        producto.setStockMinimo(5);
        return producto;
    }

    static DetallePedido crearDetalle(Producto producto, int cantidad) {
        DetallePedido detalle = new DetallePedido();
        detalle.setProducto(producto);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(producto.getPrecioUnitario());
        return detalle;
    }
}
