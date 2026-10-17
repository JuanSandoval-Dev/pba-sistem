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

    @Test
    public void aumentarCantidadDeberiaDescontarSoloLaDiferencia() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto producto = crearProducto("Bio Control", "25.00", 20);
        DetallePedido detalle = crearDetalle(producto, 3);
        pedido.agregarDetalle(detalle);

        detalle.setCantidad(5);
        detalle.antesDeActualizar();

        assertEquals(15, producto.getStockActual());
        assertEquals(5, producto.getCantidadVendida());
        assertEquals(0, new BigDecimal("125.00")
            .compareTo(detalle.getSubtotal()));
    }

    @Test
    public void disminuirCantidadDeberiaDevolverLaDiferencia() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto producto = crearProducto("Bio Control", "25.00", 20);
        DetallePedido detalle = crearDetalle(producto, 5);
        pedido.agregarDetalle(detalle);

        detalle.setCantidad(2);
        detalle.antesDeActualizar();

        assertEquals(18, producto.getStockActual());
        assertEquals(2, producto.getCantidadVendida());
    }

    @Test
    public void cambiarProductoDeberiaAjustarAmbosStocks() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto productoAnterior =
            crearProducto("Producto anterior", "25.00", 20);
        Producto productoNuevo =
            crearProducto("Producto nuevo", "30.00", 10);
        DetallePedido detalle = crearDetalle(productoAnterior, 3);
        pedido.agregarDetalle(detalle);

        detalle.setProducto(productoNuevo);
        detalle.setCantidad(4);
        detalle.setPrecioUnitario(productoNuevo.getPrecioUnitario());
        detalle.antesDeActualizar();

        assertEquals(20, productoAnterior.getStockActual());
        assertEquals(0, productoAnterior.getCantidadVendida());
        assertEquals(6, productoNuevo.getStockActual());
        assertEquals(4, productoNuevo.getCantidadVendida());
    }

    @Test
    public void eliminarDetalleDeberiaDevolverStock() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto producto = crearProducto("Bio Control", "25.00", 20);
        DetallePedido detalle = crearDetalle(producto, 3);
        pedido.agregarDetalle(detalle);

        detalle.antesDeEliminar();

        assertEquals(20, producto.getStockActual());
        assertEquals(0, producto.getCantidadVendida());
    }

    @Test
    public void noDeberiaPermitirStockNegativo() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto producto = crearProducto("Bio Control", "25.00", 2);
        DetallePedido detalle = crearDetalle(producto, 3);

        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> pedido.agregarDetalle(detalle));

        assertEquals(
            "No hay stock suficiente para el producto seleccionado.",
            error.getMessage());
        assertEquals(2, producto.getStockActual());
        assertEquals(0, producto.getCantidadVendida());
    }

    @Test
    public void cambiarProductoSinStockNoDeberiaAlterarElAnterior() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto productoAnterior =
            crearProducto("Producto anterior", "25.00", 20);
        Producto productoSinStock =
            crearProducto("Producto sin stock", "30.00", 1);
        DetallePedido detalle = crearDetalle(productoAnterior, 3);
        pedido.agregarDetalle(detalle);

        detalle.setProducto(productoSinStock);
        detalle.setCantidad(4);
        detalle.setPrecioUnitario(productoSinStock.getPrecioUnitario());

        assertThrows(
            IllegalArgumentException.class,
            detalle::antesDeActualizar);
        assertEquals(17, productoAnterior.getStockActual());
        assertEquals(1, productoSinStock.getStockActual());
    }

    @Test
    public void modificarPrecioDeberiaRecalcularSubtotal() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto producto = crearProducto("Bio Control", "25.00", 20);
        DetallePedido detalle = crearDetalle(producto, 3);
        pedido.agregarDetalle(detalle);

        detalle.setPrecioUnitario(new BigDecimal("30.00"));
        detalle.antesDeActualizar();

        assertEquals(0, new BigDecimal("90.00")
            .compareTo(detalle.getSubtotal()));
        assertEquals(17, producto.getStockActual());
    }

    @Test
    public void antesDeGuardarDeberiaUsarPrecioDelProducto() {
        Pedido pedido = PedidoTest.crearPedido();
        Producto producto = crearProducto("Bio Control", "25.00", 20);
        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setProducto(producto);
        detalle.setCantidad(2);
        pedido.getDetalles().add(detalle);

        detalle.antesDeGuardar();

        assertEquals(18, producto.getStockActual());
        assertEquals(0, new BigDecimal("50.00")
            .compareTo(detalle.getSubtotal()));
        assertEquals(0, new BigDecimal("50.00")
            .compareTo(pedido.getTotal()));
    }

    @Test
    public void postLoadDeberiaGuardarCantidadAnterior() {
        Producto producto = crearProducto("Bio Control", "25.00", 17);
        producto.setCantidadVendida(3);
        DetallePedido detalle = crearDetalle(producto, 3);
        detalle.guardarValoresAnteriores();

        detalle.setCantidad(5);
        detalle.antesDeActualizar();

        assertEquals(15, producto.getStockActual());
        assertEquals(5, producto.getCantidadVendida());
    }

    @Test
    public void eliminarDetalleNoRegistradoNoDeberiaCambiarStock() {
        Producto producto = crearProducto("Bio Control", "25.00", 20);
        DetallePedido detalle = crearDetalle(producto, 3);

        detalle.antesDeEliminar();

        assertEquals(20, producto.getStockActual());
        assertEquals(0, producto.getCantidadVendida());
    }

    @Test
    public void detalleSinProductoDeberiaFallar() {
        DetallePedido detalle = new DetallePedido();
        detalle.setCantidad(1);
        detalle.setPrecioUnitario(new BigDecimal("10.00"));

        assertThrows(
            IllegalStateException.class,
            detalle::registrarVenta);
    }

    @Test
    public void cantidadCeroDeberiaFallar() {
        Producto producto = crearProducto("Bio Control", "25.00", 20);
        DetallePedido detalle = crearDetalle(producto, 0);

        assertThrows(
            IllegalArgumentException.class,
            detalle::registrarVenta);
        assertEquals(20, producto.getStockActual());
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
